package ipca.example.suspendnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

//
// Created by lourencogomes on 10/11/2020.
//


class MainActivityViewModel: ViewModel() {

    private var _articlesType: MutableLiveData<String> = MutableLiveData()

    val articles = _articlesType.switchMap {
        Backend.fetchArticles(it)
    }

    fun setArticlesType(articlesType: String) {
        val update = articlesType
        if (_articlesType.value == update) return
        _articlesType.value = update
    }

}