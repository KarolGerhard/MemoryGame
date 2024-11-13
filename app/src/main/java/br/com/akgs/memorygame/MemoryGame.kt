package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.concurrent.TimeUnit


class MemoryGame(private val context: Context) {

    private val cards: MutableList<Card> = mutableListOf()
    private var firstFlippedCard: Card? = null
    private var clickCard: Int = 0
    var attempts: Int = 32
    private var pairs: Int = 0
    private val images = listOf(
        "card-leafeon.png", "card-espeon.png", "card-umbreon.png",
        "card-glaceon.png", "card-sylveon.png", "card-jolteon.png",
        "card-vaporeon.png", "card-flareon.png"
    )
    private val bitmaps = images.map { loadBitmap(it) }
    private val defaultImage = loadBitmap("costa-card.png")
    private val imageChecked = loadBitmap("card-white.png")


    init {
        defaultImage

        for (i in 0 until 16) {
            val bitmap = if (i < 8) bitmaps[i] else bitmaps[i - 8]
            cards.add(Card(bitmap, defaultImage, defaultImage))
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
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
    }


  fun changeCardImage(card: Card): Boolean {

        if (card.currentImage == imageChecked) return false
        val isDefaultImage = card.currentImage == card.defaultImage

        card.currentImage =
            if (isDefaultImage) card.image else card.defaultImage
        card.isFlipped = isDefaultImage

        return isDefaultImage
    }

    fun getCards(): List<Card> = cards

    fun isBlankImage(card: Card): Boolean {
        return card.currentImage == imageChecked
    }

    fun checkForMatch(card: Card): Boolean {
         if (clickCard.rem(2) == 0 && card.isFlipped) {
            TimeUnit.MILLISECONDS.sleep(400)
            attempts = maxOf(0, attempts - 1)
            val isMatch = card.image == firstFlippedCard?.image
            if (isMatch) {
                imageChecked()
                card.isMatched = true
                pairs++
            } else {

                cards.forEach {
                    if (it.currentImage != imageChecked) {
                        it.isFlipped = false
                        it.currentImage = it.defaultImage
                    }
                }
            }
            return isMatch
        }
        return false
    }

    fun setFirstFlippedCard(card: Card) {
        clickCard++
        if (clickCard.rem(2) == 1) {
            firstFlippedCard = card
        }
    }


    fun checkFinishMatched(): Boolean {
        return pairs == 8
    }

    fun hasNotAttempts(): Boolean {
        return attempts == 0
    }

    private fun imageChecked() {
        cards.forEach {
            if (it.isFlipped) {
                it.currentImage = imageChecked
                it.isFlipped = false
            }
        }
    }

}