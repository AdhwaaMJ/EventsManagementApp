package com.project.myeventsmanagementapp.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = {
            onSearch(it)
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search for task")
        },
        leadingIcon = {
                      Icon(imageVector = Icons.Default.Search, contentDescription = "" )
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                modifier = Modifier.clickable{
                    if (query != ""){
                        query = ""
                    }
                })
        })
    {}
}

//@Preview
//@Composable
//fun SearchPre(){
//    Search()
//}