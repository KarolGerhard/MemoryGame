package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent


class Cards(private val context: Context, private var x: Float, private var y: Float) : GameObject {

    private val cards: MutableList<Card> = mutableListOf()
    private var firstFlippedCard: Card? = null
    private val game = MemoryGame(context)
    private val paint = Paint()


    override fun update(et: Float) {}


    override fun render(canvas: Canvas) {
        val cards = game.getCards()
        val cols = 4
        val rows = 4
        val cardWidth = canvas.width / cols
        val cardHeight = canvas.height / rows
        val spacing = 10


        for (i in cards.indices) {
            val card = cards[i]
            val col = i % cols
            val row = i / cols
            val left = col * cardWidth
            val top = row * cardHeight
            val right = left + cardWidth
            val bottom = top + cardHeight

            canvas.drawBitmap(
                card.currentImage,
                null,
                Rect(left + spacing, top + spacing, right - spacing, bottom - spacing),
                paint
            )

//            if (card.isFlipped || card.isMatched) {
//                canvas.drawBitmap(card.image, null, android.graphics.Rect(left, top, right, bottom), paint)
//            } else {
//                paint.color = Color.GRAY
//                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
//            }
        }
    }


    override fun handleEvent(event: Int, x: Float, y: Float) {
        // quando clicar em um card, pegue o index do card clicado

        if (event == MotionEvent.ACTION_DOWN) {
            val cols = 4
            val rows = 4
//            val cardWidth = x.toInt() / cols
//            val cardHeight = y.toInt() / rows

            val cardWidth = context.resources.displayMetrics.widthPixels / cols
            val cardHeight = context.resources.displayMetrics.heightPixels / rows

            val cardIndex = (y.toInt() / cardHeight) * cols + (x.toInt() / cardWidth)
            Log.d("App", "Card index: $cardIndex")


            //pegar o index do card clicado
            // , onde o index é a posição do card na lista de cards e o index do click não pode ser maior que o index do card
//            if (cardIndex < game.getCards().size) {
            val card = game.getCards()[cardIndex]

            Log.d("App", "Card pos index: $card")

//            card.image =
//                if (card.image == game.defaultImage) game.bitmaps[cards.indexOf(card)] else game.defaultImage
            val changeCard = game.changeCardImage(card)
            Log.d("App", "Card change: $changeCard")

            if (changeCard) {
//                game.flipCard(card)
                game.checkForMatch(card)
                game.removeMatchedCards(card)
            }

//            game.flipCard(card)
//            game.checkForMatch(card)
//            game.removeMatchedCards(card)
//            }
            //se o index do click for menor que o index do card clicado, o card é virado

//            val card = game.getCards()[cardIndex]
//            game.flipCard(card)
//            invalidate()
        }
    }


//
//
}
