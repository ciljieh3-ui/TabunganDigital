package com.example

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class KantongAssetTest {

    private fun findAsset(relativePath: String): File {
        val candidates = listOf(
            File("src/main/assets", relativePath),
            File("app/src/main/assets", relativePath),
            File("../app/src/main/assets", relativePath)
        )
        return candidates.firstOrNull { it.exists() }
            ?: throw AssertionError("Asset file $relativePath not found in any candidate path: $candidates")
    }

    @Test
    fun testIndexHtmlExistsAndHasRequiredContainers() {
        val file = findAsset("index.html")
        assertTrue("index.html should exist", file.exists())
        val content = file.readText(Charsets.UTF_8)
        assertTrue("index.html should have #app container", content.contains("id=\"app\""))
        assertTrue("index.html should have #view main section", content.contains("id=\"view\""))
        assertTrue("index.html should have #nav navigation bar", content.contains("id=\"nav\""))
        assertTrue("index.html should have #sw modal sheet wrapper", content.contains("id=\"sw\""))
        assertTrue("index.html should have #toast notification", content.contains("id=\"toast\""))
        assertTrue("index.html should load app.css", content.contains("href=\"app.css\""))
        assertTrue("index.html should load app.js", content.contains("src=\"app.js\""))
    }

    @Test
    fun testAppCssExistsAndHasEssentialStyles() {
        val file = findAsset("app.css")
        assertTrue("app.css should exist", file.exists())
        val cssContent = file.readText(Charsets.UTF_8)
        assertTrue("app.css should define root variables", cssContent.contains(":root"))
        assertTrue("app.css should define dark background #08080B", cssContent.contains("#08080B"))
        assertTrue("app.css should define light theme variables", cssContent.contains("[data-theme=light]"))
        assertTrue("app.css should define animated scene styles", cssContent.contains(".scene"))
        assertTrue("app.css should define savings jars styles", cssContent.contains(".jar"))
        assertTrue("app.css should have font-face declaration", cssContent.contains("@font-face"))
    }

    @Test
    fun testAppJsExistsAndContainsCoreApplicationLogic() {
        val file = findAsset("app.js")
        assertTrue("app.js should exist", file.exists())
        val jsContent = file.readText(Charsets.UTF_8)
        assertTrue("app.js should define storage KEY kantong-v2", jsContent.contains("kantong-v2"))
        assertTrue("app.js should define CATS categories", jsContent.contains("CATS"))
        assertTrue("app.js should define Android back button handler", jsContent.contains("window.handleAndroidBack"))
        assertTrue("app.js should define native AndroidBridge integrations", jsContent.contains("AndroidBridge"))
        assertTrue("app.js should define seed function for default data", jsContent.contains("function seed"))
        assertTrue("app.js should define recurring transaction processing", jsContent.contains("function applyRec"))
    }

    @Test
    fun testBundledFontExistsAndIsReadable() {
        val file = findAsset("fonts/plus_jakarta_sans.ttf")
        assertTrue("Font file should exist", file.exists())
        assertTrue("Font file size should be greater than 10KB", file.length() > 10000)
    }
}
