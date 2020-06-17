package com.example.repository.domain

import com.example.repository.util.smartTruncate

data class DevByteVideo(val title : String,
                         val description : String,
                         val url : String,
                         val updated : String,
                         val thumbnail : String){

     val shortDescription : String
         get() = description.smartTruncate(200)
 }