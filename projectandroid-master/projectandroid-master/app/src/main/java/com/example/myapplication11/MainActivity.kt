package com.example.myapplication11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication11.models.Product
import com.example.myapplication11.ui.theme.MyApplication11Theme
import com.example.myapplication11.viewmodel.ProductUiState
import com.example.myapplication11.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication11Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(productViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(productViewModel: ProductViewModel) {
    val uiState by productViewModel.uiState.collectAsState()

    // Call fetchProducts() to load data
    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your move makes someone's life better") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF81C784))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                when (uiState) {
                    is ProductUiState.Loading -> {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 20.sp
                        )
                    }
                    is ProductUiState.Success -> {
                        val products = (uiState as ProductUiState.Success).produits
                        products.forEach { product ->
                            ProductCard(product)
                        }
                    }
                    is ProductUiState.Error -> {
                        Text(
                            text = "An error occurred while fetching products.",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display product image if available (replace with actual image loading logic)
            // Image(painter = rememberImagePainter(product.image), contentDescription = "Product Image")

            Spacer(modifier = Modifier.height(8.dp))

            product.nom?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            product.description?.let { Text(text = it) }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: ${product.prix} USD",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Get It Now")
            }
        }
    }
}
