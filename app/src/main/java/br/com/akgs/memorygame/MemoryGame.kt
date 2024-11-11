package br.com.akgs.memorygame

import android.graphics.Bitmap

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.delay

class MemoryGame(private val context: Context) {

    private val cards: MutableList<Card> = mutableListOf()
    private var firstFlippedCard: Card? = null
    val images = listOf(
        "card-leafeon.png", "card-espeon.png", "card-umbreon.png",
        "card-glaceon.png", "card-sylveon.png", "card-jolteon.png",
        "card-vaporeon.png", "card-flareon.png"
    )
    val bitmaps = images.map { loadBitmap(it) }
    val defaultImage = loadBitmap("costa-card.png")


    init {
//        val defaultImage = loadBitmap("costa-card.png")
//        val images = listOf(
//            "card-leafeon.png", "card-espeon.png", "card-umbreon.png",
//            "card-glaceon.png", "card-sylveon.png", "card-jolteon.png",
//            "card-vaporeon.png", "card-flareon.png"
//        )
//        val bitmaps = images.map { loadBitmap(it) }
//        bitmaps
        defaultImage
//        cards.addAll(bitmaps.flatMap { listOf(Card(it), Card(it)) })
//        cards.shuffle()

        for (i in 0 until 16) {
            val bitmap = if (i < 8) bitmaps[i] else bitmaps[i - 8]
            cards.add(Card(defaultImage, bitmap, defaultImage))
        }
        cards.shuffle()
    }

    private fun loadBitmap(file: String): Bitmap {
        return try {
            val inputStream = context.assets.open(file)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: Exception) {
            Log.d("App", e.message ?: "Error loading image")
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
    }

    fun flipCard(card: Card) {
        if (card.isFlipped || card.isMatched) return

        card.isFlipped = true
        if (firstFlippedCard == null) {
            firstFlippedCard = card
        } else {
            if (firstFlippedCard?.image == card.image) {
                card.isMatched = true
                firstFlippedCard?.isMatched = true
            } else {
                card.isFlipped = false
                firstFlippedCard?.isFlipped = false
            }
            firstFlippedCard = null
        }
    }


    //fazer uma funcao para trocar a imagem default para a imagem correspondente a imagem da lista que foi preenchida no init
    fun changeCardImage(card: Card): Boolean {
        val isImage = card.currentImage == card.defaultImage
        card.currentImage =
            if (isImage) card.image else card.defaultImage
        return isImage
    }

    //fazer uma funcao que quando houver o clique trocar de imagem, ao clicar novamente no card fica alterando entre a imagem e a imagem padrão, a lista nao pode ser random, tem que ser a mesma imagem

    fun getCards(): List<Card> = cards

    fun checkForMatch(card: Card) {
        if (firstFlippedCard != null && firstFlippedCard != card) {
            val isMatch = firstFlippedCard?.image == card.image
            card.isMatched = isMatch
            firstFlippedCard?.isMatched = isMatch
            firstFlippedCard = null
        }
    }


    fun removeMatchedCards(card: Card) {
        cards.removeAll { it.image == card.currentImage }
    }


}