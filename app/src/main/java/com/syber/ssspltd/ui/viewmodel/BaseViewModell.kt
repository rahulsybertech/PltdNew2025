package com.syber.ssspltd.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.app.naturalhigh.out.AuthRepository

open class BaseViewModell(
    protected val repository: AuthRepository
) : ViewModel()
