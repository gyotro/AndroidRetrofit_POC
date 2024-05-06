package com.sap.testretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.presentation.screen.TokenViewModel
import com.sap.testretrofit.presentation.ui.theme.TestRetrofitTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel = getViewModel<TokenViewModel>()
                    TokenData(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TokenData(modifier: Modifier = Modifier, viewModel: TokenViewModel) {
    val interfaces = viewModel.cpiMoniFlow.collectAsState().value
    when(interfaces) {
        is BaseModel.Error -> {
            Text(text = "Base Model Error: ${interfaces.error}")
        }

        BaseModel.Loading -> {
            Text(text = "Base Model Loading")
        }

        is BaseModel.Success -> {
            LazyColumn {
                items(interfaces.data.d.results) { item ->
                    Text(text = item.integrationFlowName)
                }
            }
        }

        null -> {
            Text(text = "Null case")
        }
    }
}

