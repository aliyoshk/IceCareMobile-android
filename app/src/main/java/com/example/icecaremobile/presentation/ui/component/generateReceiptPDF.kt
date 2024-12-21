package com.example.icecaremobile.presentation.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import java.io.File
import java.io.FileOutputStream

fun generateReceiptPDF(
    context: Context,
    onComplete: () -> Unit,
    composableContent: @Composable () -> Unit
) {
    try {
        val pdfDocument = PdfDocument()
        val filePath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Receipt.pdf"
        )

        // Create a ComposeView to render the composable
        val composeView = ComposeView(context).apply {
            setContent { composableContent() }
        }

        // Measure and layout the view
        composeView.measure(
            View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY)
        )
        composeView.layout(0, 0, 1080, 1920)

        // Create a bitmap to draw the view
        val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        composeView.draw(canvas)

        // Write the bitmap to a PDF page
        val pageInfo = PdfDocument.PageInfo.Builder(1080, 1920, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val pdfCanvas = page.canvas
        pdfCanvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

        // Save the PDF to the specified file path
        FileOutputStream(filePath).use { output ->
            pdfDocument.writeTo(output)
        }

        pdfDocument.close()

        Toast.makeText(context, "PDF saved to ${filePath.absolutePath}", Toast.LENGTH_LONG).show()
        onComplete()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error saving PDF: ${e.message}", Toast.LENGTH_LONG).show()
        Log.e("ReceiptScreen", "Error saving PDF: ${e.message}")
    }
}
