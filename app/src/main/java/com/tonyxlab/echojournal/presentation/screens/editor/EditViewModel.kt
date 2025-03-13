package com.tonyxlab.echojournal.presentation.screens.editor

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel(assistedFactory = EditViewModel.EditorViewModelFactory::class)
class EditViewModel @AssistedInject constructor(
    @Assisted("audioPath")
    private val audioPath: String,
    @Assisted("id")
    private val id: String

) : ViewModel() {


    @AssistedFactory
    interface EditorViewModelFactory {

        fun create(
            @Assisted("audioPath")
            audioPath: String,
            @Assisted("id")
            id: String
        ): EditViewModel

    }
}
