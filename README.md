
# Lista de Compras (Loja)

Aplicação mobile desenvolvida em Kotlin utilizando Jetpack Compose e SQLite para gerenciamento de listas de compras organizadas por lojas.

O projeto foi desenvolvido como atividade prática da disciplina de Programação de Aplicativos Mobile (PAM II), com foco na utilização de navegação entre telas, persistência de dados local e relacionamento entre tabelas utilizando Foreign Key.

## Objetivo

A aplicação permite cadastrar lojas e criar listas de compras vinculadas a cada uma delas, facilitando a organização dos itens de maneira separada por estabelecimento.

---

## Funcionalidades

- Cadastro de lojas
- Cadastro de itens de compra
- Organização dos itens por loja
- Navegação entre telas
- Persistência de dados com SQLite
- Interface construída inteiramente com Jetpack Compose

---

##Telas

<img width="300" height="640" alt="WhatsApp Image 2026-05-27 at 01 46 54 (1)" src="https://github.com/user-attachments/assets/05118f5b-9c07-401b-a683-7bf2d2fe60f8" >
<img width="300" height="640" alt="WhatsApp Image 2026-05-27 at 01 46 54" src="https://github.com/user-attachments/assets/02615b80-76d2-4b99-a89a-9191ae28ac85" /> <br>
<img width="300" height="640" alt="WhatsApp Image 2026-05-27 at 01 46 55 (1)" src="https://github.com/user-attachments/assets/4b10f7ba-6438-4432-bcd6-e2bc11a2f9e4" />
<img width="300" height="640" alt="WhatsApp Image 2026-05-27 at 01 46 55" src="https://github.com/user-attachments/assets/29006792-cd53-4d26-8926-0aae138e4655" />

---

## Estrutura do projeto

```text
app/src/main/java/com/mcr/listacompras/
├── database/
│ ├── AppDatabase.kt
│ └── DatabaseHelper.kt
├── model/
│ ├── Loja.kt
│ └── ItemCompra.kt
├── dao/
│ ├── LojaDao.kt
│ └── ItemCompraDao.kt
├── viewmodel/
│ └── MainViewModel.kt
├── ui/
│ ├── LojaScreen.kt
│ ├── ListaComprasScreen.kt
│ └── Theme.kt
└── MainActivity.kt
````

---

## Como executar o projeto

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/ListaComprasMCR.git
```

2. Abra o projeto no Android Studio.

3. Aguarde o Gradle sincronizar as dependências.

4. Execute a aplicação em um emulador Android ou dispositivo físico.

---


## Autor

Projeto desenvolvido por Beatriz Torres para a disciplina de PAM II.

