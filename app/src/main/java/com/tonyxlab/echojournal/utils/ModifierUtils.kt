package com.tonyxlab.echojournal.utils

import androidx.compose.ui.Modifier


fun Modifier.addConditionalModifier(condition:Boolean, modifier:Modifier.()->Modifier):Modifier {

    return if (condition){

        this.then(modifier())
    }else this



}
