package com.nandroid.noteapp.ui.fragments.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nandroid.noteapp.MyApplication
import com.nandroid.noteapp.R
import com.nandroid.noteapp.data.database.Provider
import com.nandroid.noteapp.data.models.User
import com.nandroid.noteapp.databinding.FragmentLoginBinding
import com.nandroid.noteapp.ui.fragments.notes.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val signInAccount = accountTask.getResult(ApiException::class.java)
                    val authCredential =
                        GoogleAuthProvider.getCredential(signInAccount.idToken, null)

                    auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            requireActivity().getSharedPreferences(
                                "user-login",
                                Context.MODE_PRIVATE
                            ).edit().putBoolean("user-login", true).apply()
                            GlobalScope.launch(Dispatchers.IO) {
                                val user = User(0, auth.currentUser?.email)
                                Provider.provideUserDao(MyApplication.database).insertUser(user)
                            }
                            openHomeFragment()
                        } else {
                            showProgressBar(false)
                            task.exception?.printStackTrace()
                        }
                    }
                } catch (e: ApiException) {
                    showProgressBar(false)
                    e.printStackTrace()
                }
            }
            else{
                showProgressBar(false)
            }
        }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding.parentLoginView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.parentLoginView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun openHomeFragment() {

        requireActivity().supportFragmentManager.beginTransaction().apply {
            requireActivity().supportFragmentManager.popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            add(R.id.mainFragment, HomeFragment())
            setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FirebaseApp.initializeApp(requireActivity())
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        auth = FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            handelSignIn(googleSignInClient)
        }

        return binding.root
    }

    private fun handelSignIn(googleSignInClient: GoogleSignInClient) {
        val intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(intent)
        showProgressBar(true)
    }
}