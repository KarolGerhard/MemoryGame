package br.com.akgs.memorygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent


class Cards(
    private val context: Context,
    private val screen: MainActivity.Screen,
    private var x: Float,
    private var y: Float
) : GameObject {

    private val game = MemoryGame(context)
    private val paint = Paint()

    init {
        paint.color = Color.BLUE
        paint.typeface = Fonts.pokemonFontSolid
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 48f
        paint.isFakeBoldText = true
    }


    override fun update(et: Float) {}


    override fun render(canvas: Canvas) {
        val cards = game.getCards()
        val cols = 4
        val rows = 4
        val cardWidth = canvas.width / cols
        val cardHeight = (canvas.height - 400) / rows
        val spacing = 8

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
        }
        canvas.drawText("Tentativas restantes: ", x, y + 100, paint)
        canvas.drawText(game.attempts.toString(), x, y + 150, paint)
    }


    override fun handleEvent(event: Int, x: Float, y: Float) {

        if (event == MotionEvent.ACTION_DOWN) {
            val cols = 4
            val rows = 4

            val cardWidth = context.resources.displayMetrics.widthPixels / cols
            val cardHeight = context.resources.displayMetrics.heightPixels / rows

            val cardIndex = (y.toInt() / cardHeight) * cols + (x.toInt() / cardWidth)


            val card = game.getCards()[cardIndex]


            if (game.isBlankImage(card)) {
                return
            }

            game.changeCardImage(card)
            game.setFirstFlippedCard(card)

            game.checkForMatch(card)

        }

        if (game.hasNotAttempts()) {
            screen.scene = GameOverScene(screen)
        }

        if (game.checkFinishMatched()) {
            screen.scene = FinishSuccessScene(screen)
        }


    }

}
