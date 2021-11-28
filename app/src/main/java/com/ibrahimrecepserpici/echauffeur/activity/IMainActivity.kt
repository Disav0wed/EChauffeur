package com.ibrahimrecepserpici.echauffeur.activity

import androidx.lifecycle.ViewModel



interface IMainActivity<T : ViewModel> {
    val viewModel: T
    fun navigateToMapFragment()
}