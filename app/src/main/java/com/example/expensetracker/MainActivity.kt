package com.example.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.database.ExpenseDatabase
import com.example.expensetracker.databinding.ActivityMainBinding
import com.example.expensetracker.expensedialog.ExpenseDialog
import com.example.expensetracker.expenselist.ExpenseAdapter
import com.example.expensetracker.expenselist.ExpenseListViewModel
import com.example.expensetracker.expenselist.ExpenseListViewModelFactory
import com.example.expensetracker.expenselist.ExpenseListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    private val exchangeRateViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.summaryButton.setOnClickListener { goToSummary() }

        exchangeRateViewModel.response.observe(this, Observer {
            it?.let {
                binding.aleluia.text = it.toString()
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        // getting the value of gso inside the GoogleSigninClient
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso)

        binding.logout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.fabAdd.setOnClickListener {
            val dialog = ExpenseDialog.newInstance(0)

            dialog.show(supportFragmentManager, "tag")
        }

        //get view model
        val application = requireNotNull(this).application
        val dataSource = ExpenseDatabase.getInstance(application).expenseDAO
        val viewModelFactory = ExpenseListViewModelFactory(dataSource, application)
        val expenseListViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ExpenseListViewModel::class.java)

        val adapter = ExpenseAdapter(ExpenseListener { expenseId ->
            expenseListViewModel.onExpenseClicked(expenseId, supportFragmentManager)
        })
        binding.expenseList.adapter = adapter

        expenseListViewModel.actualExpenses.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun goToSummary() {
        val intent = Intent(this, Summary::class.java)
        startActivity(intent)
    }
}