package com.vodafone.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    placeholder: String = "Search"
) {
    val searchText by searchState.searchText.collectAsState()
    val isSearching by searchState.isSearching.collectAsState()
    val searchList by searchState.searchList.collectAsState()

    SearchBar(
        modifier = modifier
            .background(Color.Transparent, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shadowElevation = 3.dp,
        query = searchText,
        placeholder = { Text(placeholder) },
        onQueryChange = searchState.onSearchTextChange,
        onSearch = searchState.onSearch, //invoked when the input service triggers the ImeAction.Search action
        active = isSearching,
        onActiveChange = { searchState.onToggleSearch() },
        trailingIcon = {
            if (isSearching)
                Icon(
                    modifier = Modifier.clickable {
                        searchState.onToggleSearch()
                    },
                    imageVector = Icons.Default.Close, contentDescription = null
                )
        },
        leadingIcon = {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        searchState.onSearch(searchText)
                    }) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_search_category_default),
                        contentDescription = null
                    )
                }
                DividerVertical(
                    modifier = Modifier.height(25.dp),
                    color = Color.Gray
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(searchList) { text ->
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        )
                        .clickable {
                            searchState.onSearchTextChange.invoke("")
                            searchState.onSearchTextChange.invoke(text)
                        }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchViewPreview() {
    SearchView(
        modifier = Modifier.padding(20.dp),
        searchState = SearchState()
    )
}

@Preview(showBackground = true, locale = "ar")
@Composable
private fun SearchViewPreviewArabic() {
    SearchViewPreview()
}


data class SearchState(
    val isSearching: StateFlow<Boolean> = MutableStateFlow(false),
    val searchText: StateFlow<String> = MutableStateFlow(""),
    val searchList: StateFlow<List<String>> = MutableStateFlow(emptyList()),
    val onSearchTextChange: (String) -> Unit = {},
    var onSearch: (String) -> Unit = {},
    val onToggleSearch: () -> Unit = {}
)