package health.fit.bodyz.app.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import health.fit.bodyz.app.R

class NoInternedExplanationFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_interned_explanation, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NoInternedExplanationFragment()
    }
}