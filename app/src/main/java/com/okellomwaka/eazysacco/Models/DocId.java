package com.okellomwaka.eazysacco.Models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class DocId {

        @Exclude
        public String DocId;
        public <T extends DocId> T withID(@NonNull final String id){
            this.DocId = id;
            return(T) this;
        }


}
