package ge.bobokvadze.usersapp

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ge.bobokvadze.usersapp.databinding.LayoutEditFragmentBinding

class EditFragment : BaseFragment<LayoutEditFragmentBinding>(LayoutEditFragmentBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun start() {
        binding.btnSave.setOnClickListener {
            if (binding.etName.text.toString().isNotEmpty()) {
                viewModel.save(binding.etName.text.toString())
            } else {
                Toast.makeText(requireContext(), "Input is clear", Toast.LENGTH_LONG).show()
            }
        }
    }
}