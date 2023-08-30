package health.fit.bodyz.app.view.fragments

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.textview.MaterialTextView
import health.fit.bodyz.app.FitnessApplication
import health.fit.bodyz.app.R
import health.fit.bodyz.app.data.WorkoutModel
import health.fit.bodyz.app.data.db.entity.WorkoutEntity
import health.fit.bodyz.app.view.adapter.WorkoutListAdapter
import health.fit.bodyz.app.view.custom.TimePicker
import health.fit.bodyz.app.view.custom.TimerView
import kotlinx.coroutines.launch

class TimerAndScheduleFragment : Fragment() {


    private lateinit var timePicker: TimePicker
    private lateinit var timer: TimerView
    private lateinit var timerTime: MaterialTextView
    private lateinit var divider: View
    private lateinit var description: TextView
    private lateinit var addNew: TextView
    var countDownTimerCached = 0L
    private lateinit var recyclerView: RecyclerView
    private lateinit var closeTimer: ImageButton
    private lateinit var startTimer: ImageButton
    lateinit var countDownTimer: CountDownTimer
    enum class ScreenStatus {
        Timers,
        PickTime,
        Paused
    }

    private var status = ScreenStatus.PickTime

    private val db by lazy {
        (requireActivity().application as FitnessApplication).getDbInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_timer_and_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdapter = WorkoutListAdapter {
            timePicker.setTime(it.time)
        }

        timePicker = view.findViewById<TimePicker>(R.id.stubTimePicker)
        timer = view.findViewById<TimerView>(R.id.stubTimerView)
        timerTime = view.findViewById<MaterialTextView>(R.id.stubTimerTime)
        divider = view.findViewById<View>(R.id.divider)
        description = view.findViewById<TextView>(R.id.stubDescriptionAddNew)
        addNew =  view.findViewById<TextView>(R.id.stubTvAddNew)
        recyclerView = view.findViewById<RecyclerView>(R.id.stubRvWorkouts)
        closeTimer = view.findViewById<ImageButton>(R.id.stubBtnCloseTimer)
        startTimer = view.findViewById<ImageButton>(R.id.stubBtnStartTimer)


        timer.setOnCompleteCallback { closeTimer() }

        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        db.getHRRecords().asLiveData().observe(viewLifecycleOwner) {
            mAdapter.submitList(it.map {
                WorkoutModel(it.desc, it.timestamp)
            })
        }

        view.findViewById<TextView>(R.id.stubTvAddNew).setOnClickListener {
            WorkoutDialog.showEnterWorkoutNameDialog(requireContext()) {
                lifecycleScope.launch {
                    db.addNewRecord(
                        WorkoutEntity(
                        desc = it, timestamp = timePicker.getTime()
                    )
                    )
                }
            }
        }

        view.findViewById<ImageButton>(R.id.stubBtnStartTimer).setOnClickListener {
            when (status) {
                ScreenStatus.PickTime -> {
                    status = ScreenStatus.Timers
                    (it as ImageButton).setImageResource(R.drawable.baseline_pause_24)
                    val timeString = timePicker.getTime()
                    val time = TimePicker.convertStringToMillis(timeString)
                    timerTime.text = timeString
                    startTimerForTextView(time)
                    timer.startSpinnerAnimation(time)
                    timePicker.visibility = View.GONE
                    timer.visibility = View.VISIBLE
                    timerTime.visibility = View.VISIBLE
                    divider.visibility = View.GONE
                    description.visibility = View.GONE
                    addNew.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    closeTimer.visibility = View.VISIBLE
                }
                ScreenStatus.Paused -> {
                    startTimer.setImageResource(R.drawable.baseline_pause_24)
                    startTimerForTextView(countDownTimerCached)
                    timer.resumeValueAnimator()
                    status = ScreenStatus.Timers
                }
                ScreenStatus.Timers -> {
                    startTimer.setImageResource(R.drawable.baseline_play_arrow_24)
                    countDownTimer.cancel()
                    status = ScreenStatus.Paused
                    timer.pauseValueAnimator()
                }
            }
            closeTimer.setOnClickListener {
                closeTimer()
            }

        }
    }

    fun startTimerForTextView(timeMillis: Long) {
        countDownTimer =  object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownTimerCached = millisUntilFinished
                timerTime.text = TimePicker.getMinusSecondValue(millisUntilFinished)
            }

            override fun onFinish() =Unit
        }


        countDownTimer.start()
    }

    private fun closeTimer() {
        status = ScreenStatus.PickTime
        countDownTimer.cancel()
        timer.stopTimer()
        startTimer.setImageResource(R.drawable.baseline_play_arrow_24)
        timePicker.visibility = View.VISIBLE
        timer.visibility = View.GONE
        timerTime.visibility = View.GONE
        divider.visibility = View.VISIBLE
        description.visibility = View.VISIBLE
        addNew.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
        closeTimer.visibility = View.INVISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TimerAndScheduleFragment()
    }
}