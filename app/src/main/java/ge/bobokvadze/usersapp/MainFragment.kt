package ge.bobokvadze.usersapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.activityViewModels
import ge.bobokvadze.usersapp.databinding.LayoutMainFragmentBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

class MainFragment : BaseFragment<LayoutMainFragmentBinding>(LayoutMainFragmentBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun start() {
        val adapter = UsersAdapter()

        viewModel.collect {
            it.handleView(binding, adapter)
        }
    }
}

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        start()
        observes()
        return binding.root
    }

    abstract fun start()
    open fun observes() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
