package com.mcr.listacompras.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mcr.listacompras.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaComprasScreen(
    lojaId: Int,
    nomeLoja: String,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val itens by viewModel.itens.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var nomeItem by remember { mutableStateOf("") }
    var qtdItem by remember { mutableStateOf("1") }
    var precoItem by remember { mutableStateOf("0.0") }

    LaunchedEffect(lojaId) { viewModel.carregarItens(lojaId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("✝ $nomeLoja") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Adicionar item")
            }
        }
    ) { padding ->
        if (itens.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Lista vazia\nToque em + para adicionar itens",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(itens) { item ->
                    Card(
                        Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.comprado,
                                onCheckedChange = {
                                    viewModel.marcarComprado(item.id, it, lojaId)
                                }
                            )
                            Column(Modifier.weight(1f).padding(start = 8.dp)) {
                                Text(
                                    item.nome,
                                    style = MaterialTheme.typography.titleMedium,
                                    textDecoration = if (item.comprado)
                                        TextDecoration.LineThrough else TextDecoration.None
                                )
                                Text(
                                    "Qtd: ${item.quantidade}  •  R$ ${"%.2f".format(item.preco)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { viewModel.deletarItem(item.id, lojaId) }) {
                                Icon(Icons.Default.Delete, "Deletar",
                                    tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Novo Item") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = nomeItem, onValueChange = { nomeItem = it },
                        label = { Text("Nome do item") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = qtdItem, onValueChange = { qtdItem = it },
                        label = { Text("Quantidade") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = precoItem, onValueChange = { precoItem = it },
                        label = { Text("Preço (R$)") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (nomeItem.isNotBlank()) {
                        viewModel.adicionarItem(
                            nomeItem.trim(),
                            qtdItem.toIntOrNull() ?: 1,
                            precoItem.toDoubleOrNull() ?: 0.0,
                            lojaId
                        )
                        nomeItem = ""; qtdItem = "1"; precoItem = "0.0"; showDialog = false
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }
}