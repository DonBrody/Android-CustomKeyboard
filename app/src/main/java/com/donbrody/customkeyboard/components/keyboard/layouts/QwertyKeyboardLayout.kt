package com.donbrody.customkeyboard.components.keyboard.layouts

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.donbrody.customkeyboard.components.keyboard.KeyboardListener
import com.donbrody.customkeyboard.components.keyboard.controllers.KeyboardController
import com.donbrody.customkeyboard.components.utilities.ComponentUtils

/**
 * Created by Don.Brody on 7/20/18.
 */
class QwertyKeyboardLayout(context: Context, controller: KeyboardController?) :
        KeyboardLayout(context, controller) {

    constructor(context: Context): this(context, null)

    private var capsState: CapsState = CapsState.CAPS_DISABLED
    private var symbolsState: SymbolState = SymbolState.SYMBOLS_DISABLED
    private val columnWidth = 0.09f

    private enum class CapsState {
        CAPS_DISABLED,
        CAPS_ENABLED,
        CAPS_LOCK_ENABLED
    }

    private enum class SymbolState {
        SYMBOLS_DISABLED,
        SYMBOL_ONE_DISPLAYED,
        SYMBOL_TWO_DISPLAYED,
    }

    init {
        controller?.registerListener(object: KeyboardListener {
            override fun characterClicked(c: Char) {
                if (capsState === CapsState.CAPS_ENABLED) {
                    capsState = CapsState.CAPS_DISABLED
                    createKeyboard()
                }
            }

            override fun specialKeyClicked(key: KeyboardController.SpecialKey) {
                when(key) {
                    KeyboardController.SpecialKey.CAPS -> {
                        capsState = when(capsState) {
                            CapsState.CAPS_DISABLED -> {
                                CapsState.CAPS_ENABLED
                            }
                            CapsState.CAPS_ENABLED -> {
                                CapsState.CAPS_LOCK_ENABLED
                            }
                            CapsState.CAPS_LOCK_ENABLED -> {
                                CapsState.CAPS_DISABLED
                            }
                        }
                        createKeyboard()
                    }
                    KeyboardController.SpecialKey.SYMBOL -> {
                        symbolsState = when(symbolsState) {
                            SymbolState.SYMBOLS_DISABLED -> {
                                SymbolState.SYMBOL_ONE_DISPLAYED
                            }
                            SymbolState.SYMBOL_ONE_DISPLAYED -> {
                                SymbolState.SYMBOL_TWO_DISPLAYED
                            }
                            SymbolState.SYMBOL_TWO_DISPLAYED -> {
                                SymbolState.SYMBOL_ONE_DISPLAYED
                            }
                        }
                        createKeyboard()
                    }
                    KeyboardController.SpecialKey.ALPHA -> {
                        symbolsState = SymbolState.SYMBOLS_DISABLED
                        createKeyboard()
                    }
                    else -> return
                }
            }
        })
    }

    override fun createRows(): List<LinearLayout> {
        if (symbolsState !== SymbolState.SYMBOLS_DISABLED) {
            return when(symbolsState) {
                SymbolState.SYMBOL_ONE_DISPLAYED -> {
                    createSymbolsOneRows()
                }
                SymbolState.SYMBOL_TWO_DISPLAYED -> {
                    createSymbolsTwoRows()
                }
                else -> {
                    ArrayList() // this will never happen
                }
            }
        } else {
            return when(capsState) {
                CapsState.CAPS_DISABLED -> {
                    createLowerCaseRows()
                }
                CapsState.CAPS_ENABLED -> {
                    createUpperCaseRows()
                }
                CapsState.CAPS_LOCK_ENABLED -> {
                    createUpperCaseRows()
                }
            }
        }
    }

    private fun createLowerCaseRows(): List<LinearLayout> {
        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("q", columnWidth, 'q'))
        rowTwo.add(createButton("w", columnWidth, 'w'))
        rowTwo.add(createButton("e", columnWidth, 'e'))
        rowTwo.add(createButton("r", columnWidth, 'r'))
        rowTwo.add(createButton("t", columnWidth, 't'))
        rowTwo.add(createButton("y", columnWidth, 'y'))
        rowTwo.add(createButton("u", columnWidth, 'u'))
        rowTwo.add(createButton("i", columnWidth, 'i'))
        rowTwo.add(createButton("o", columnWidth, 'o'))
        rowTwo.add(createButton("p", columnWidth, 'p'))
        rowTwo.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowThree = ArrayList<View>()
        rowThree.add(createSpacer((columnWidth * 0.5f)))
        rowThree.add(createButton("a", columnWidth, 'a'))
        rowThree.add(createButton("s", columnWidth, 's'))
        rowThree.add(createButton("d", columnWidth, 'd'))
        rowThree.add(createButton("f", columnWidth, 'f'))
        rowThree.add(createButton("g", columnWidth, 'g'))
        rowThree.add(createButton("h", columnWidth, 'h'))
        rowThree.add(createButton("j", columnWidth, 'j'))
        rowThree.add(createButton("k", columnWidth, 'k'))
        rowThree.add(createButton("l", columnWidth, 'l'))
        if (hasNextFocus) {
            rowThree.add(createButton(
                    "Next", (columnWidth * 1.5f), KeyboardController.SpecialKey.NEXT))
        } else {
            rowThree.add(createButton(
                    "Done", (columnWidth * 1.5f), KeyboardController.SpecialKey.DONE))
        }

        val rowFour = ArrayList<View>()
        rowFour.add(createCapsButton())
        rowFour.add(createButton("z", columnWidth, 'z'))
        rowFour.add(createButton("x", columnWidth, 'x'))
        rowFour.add(createButton("c", columnWidth, 'c'))
        rowFour.add(createButton("v", columnWidth, 'v'))
        rowFour.add(createButton("b", columnWidth, 'b'))
        rowFour.add(createButton("n", columnWidth, 'n'))
        rowFour.add(createButton("m", columnWidth, 'm'))
        rowFour.add(createButton(",", columnWidth, ','))
        rowFour.add(createButton(".", columnWidth, '.'))
        rowFour.add(createSpacer(columnWidth))

        val rowFive = ArrayList<View>()
        rowFive.add(createButton(
                "Symbols", (columnWidth * 2.0f), KeyboardController.SpecialKey.SYMBOL))
        rowFive.add(createButton("", columnWidth * 7.0f, ' '))
        rowFive.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFive.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))

        val rows = ArrayList<LinearLayout>()
        rows.add(createNumbersRow())
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))
        rows.add(createRow(rowFive))

        return rows
    }

    private fun createUpperCaseRows(): List<LinearLayout> {
        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("Q", columnWidth, 'Q'))
        rowTwo.add(createButton("W", columnWidth, 'W'))
        rowTwo.add(createButton("E", columnWidth, 'E'))
        rowTwo.add(createButton("R", columnWidth, 'R'))
        rowTwo.add(createButton("T", columnWidth, 'T'))
        rowTwo.add(createButton("Y", columnWidth, 'Y'))
        rowTwo.add(createButton("U", columnWidth, 'U'))
        rowTwo.add(createButton("I", columnWidth, 'I'))
        rowTwo.add(createButton("O", columnWidth, 'O'))
        rowTwo.add(createButton("P", columnWidth, 'P'))
        rowTwo.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowThree = ArrayList<View>()
        rowThree.add(createSpacer((columnWidth * 0.5f)))
        rowThree.add(createButton("A", columnWidth, 'A'))
        rowThree.add(createButton("S", columnWidth, 'S'))
        rowThree.add(createButton("D", columnWidth, 'D'))
        rowThree.add(createButton("F", columnWidth, 'F'))
        rowThree.add(createButton("G", columnWidth, 'G'))
        rowThree.add(createButton("H", columnWidth, 'H'))
        rowThree.add(createButton("J", columnWidth, 'J'))
        rowThree.add(createButton("K", columnWidth, 'K'))
        rowThree.add(createButton("L", columnWidth, 'L'))
        if (hasNextFocus) {
            rowThree.add(createButton(
                    "Next", (columnWidth * 1.5f), KeyboardController.SpecialKey.NEXT))
        } else {
            rowThree.add(createButton(
                    "Done", (columnWidth * 1.5f), KeyboardController.SpecialKey.DONE))
        }

        val rowFour = ArrayList<View>()
        rowFour.add(createCapsButton())
        rowFour.add(createButton("Z", columnWidth, 'Z'))
        rowFour.add(createButton("X", columnWidth, 'X'))
        rowFour.add(createButton("C", columnWidth, 'C'))
        rowFour.add(createButton("V", columnWidth, 'V'))
        rowFour.add(createButton("B", columnWidth, 'B'))
        rowFour.add(createButton("N", columnWidth, 'N'))
        rowFour.add(createButton("M", columnWidth, 'M'))
        rowFour.add(createButton(",", columnWidth, ','))
        rowFour.add(createButton(".", columnWidth, '.'))
        rowFour.add(createSpacer(columnWidth))

        val rowFive = ArrayList<View>()
        rowFive.add(createButton(
                "Symbols", (columnWidth * 2.0f), KeyboardController.SpecialKey.SYMBOL))
        rowFive.add(createButton("", columnWidth * 7.0f, ' '))
        rowFive.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFive.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))

        val rows = ArrayList<LinearLayout>()
        rows.add(createNumbersRow())
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))
        rows.add(createRow(rowFive))

        return rows
    }

    private fun createSymbolsOneRows(): List<LinearLayout> {
        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("+", columnWidth, '+'))
        rowTwo.add(createButton("×", columnWidth, '×'))
        rowTwo.add(createButton("÷", columnWidth, '÷'))
        rowTwo.add(createButton("=", columnWidth, '='))
        rowTwo.add(createButton("%", columnWidth, '%'))
        rowTwo.add(createButton("_", columnWidth, '_'))
        rowTwo.add(createButton("€", columnWidth, '€'))
        rowTwo.add(createButton("£", columnWidth, '£'))
        rowTwo.add(createButton("¥", columnWidth, '¥'))
        rowTwo.add(createButton("₩", columnWidth, '₩'))
        rowTwo.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowThree = ArrayList<View>()
        rowThree.add(createSpacer((columnWidth * 0.5f)))
        rowThree.add(createButton("@", columnWidth, '@'))
        rowThree.add(createButton("#", columnWidth, '#'))
        rowThree.add(createButton("$", columnWidth, '$'))
        rowThree.add(createButton("/", columnWidth, '/'))
        rowThree.add(createButton("^", columnWidth, '^'))
        rowThree.add(createButton("&", columnWidth, '&'))
        rowThree.add(createButton("*", columnWidth, '*'))
        rowThree.add(createButton("(", columnWidth, '('))
        rowThree.add(createButton(")", columnWidth, ')'))
        if (hasNextFocus) {
            rowThree.add(createButton(
                    "Next", (columnWidth * 1.5f), KeyboardController.SpecialKey.NEXT))
        } else {
            rowThree.add(createButton(
                    "Done", (columnWidth * 1.5f), KeyboardController.SpecialKey.DONE))
        }

        val rowFour = ArrayList<View>()

        rowFour.add(createCapsButton())
        rowFour.add(createButton("-", columnWidth, '-'))
        rowFour.add(createButton("'", columnWidth, '\''))
        rowFour.add(createButton("\"", columnWidth, '\"'))
        rowFour.add(createButton(":", columnWidth, ':'))
        rowFour.add(createButton(";", columnWidth, ';'))
        rowFour.add(createButton("!", columnWidth, '!'))
        rowFour.add(createButton("?", columnWidth, '?'))
        rowFour.add(createButton(",", columnWidth, ','))
        rowFour.add(createButton(".", columnWidth, '.'))
        rowFour.add(createSpacer(columnWidth))

        val rowFive = ArrayList<View>()
        rowFive.add(createButton(
                "Sym (1/2)", (columnWidth * 2.0f), KeyboardController.SpecialKey.SYMBOL))
        rowFive.add(createButton("", columnWidth * 7.0f, ' '))
        rowFive.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFive.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))

        val rows = ArrayList<LinearLayout>()
        rows.add(createNumbersRow())
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))
        rows.add(createRow(rowFive))

        return rows
    }

    private fun createSymbolsTwoRows(): List<LinearLayout> {
        val rowTwo = ArrayList<View>()
        rowTwo.add(createButton("`", columnWidth, '`'))
        rowTwo.add(createButton("~", columnWidth, '~'))
        rowTwo.add(createButton("\\", columnWidth, '\\'))
        rowTwo.add(createButton("|", columnWidth, '|'))
        rowTwo.add(createButton("<", columnWidth, '<'))
        rowTwo.add(createButton(">", columnWidth, '>'))
        rowTwo.add(createButton("{", columnWidth, '{'))
        rowTwo.add(createButton("}", columnWidth, '}'))
        rowTwo.add(createButton("[", columnWidth, '['))
        rowTwo.add(createButton("]", columnWidth, ']'))
        rowTwo.add(createButton("⌫", columnWidth, KeyboardController.SpecialKey.BACKSPACE))

        val rowThree = ArrayList<View>()
        rowThree.add(createSpacer((columnWidth * 0.5f)))
        rowThree.add(createButton("▪", columnWidth, '▪'))
        rowThree.add(createButton("○", columnWidth, '○'))
        rowThree.add(createButton("●", columnWidth, '●'))
        rowThree.add(createButton("□", columnWidth, '□'))
        rowThree.add(createButton("■", columnWidth, '■'))
        rowThree.add(createButton("♤", columnWidth, '♤'))
        rowThree.add(createButton("♡", columnWidth, '♡'))
        rowThree.add(createButton("◇", columnWidth, '◇'))
        rowThree.add(createButton("♧", columnWidth, '♧'))
        if (hasNextFocus) {
            rowThree.add(createButton(
                    "Next", (columnWidth * 1.5f), KeyboardController.SpecialKey.NEXT))
        } else {
            rowThree.add(createButton(
                    "Done", (columnWidth * 1.5f), KeyboardController.SpecialKey.DONE))
        }

        val rowFour = ArrayList<View>()

        rowFour.add(createCapsButton())
        rowFour.add(createButton("☆", columnWidth, '☆'))
        rowFour.add(createButton("⊙", columnWidth, '⊙'))
        rowFour.add(createButton("⦿", columnWidth, '⦿'))
        rowFour.add(createButton("⍉", columnWidth, '⍉'))
        rowFour.add(createButton("⊛", columnWidth, '⊛'))
        rowFour.add(createButton("⟪", columnWidth, '⟪'))
        rowFour.add(createButton("⟫", columnWidth, '⟫'))
        rowFour.add(createButton("¡", columnWidth, '¡'))
        rowFour.add(createButton("¿", columnWidth, '¿'))
        rowFour.add(createSpacer(columnWidth))

        val rowFive = ArrayList<View>()
        rowFive.add(createButton(
                "Sym (2/2)", (columnWidth * 2.0f), KeyboardController.SpecialKey.SYMBOL))
        rowFive.add(createButton("", columnWidth * 7.0f, ' '))
        rowFive.add(createButton("⇦", columnWidth, KeyboardController.SpecialKey.BACK))
        rowFive.add(createButton("⇨", columnWidth, KeyboardController.SpecialKey.FORWARD))

        val rows = ArrayList<LinearLayout>()
        rows.add(createNumbersRow())
        rows.add(createRow(rowTwo))
        rows.add(createRow(rowThree))
        rows.add(createRow(rowFour))
        rows.add(createRow(rowFive))

        return rows
    }

    private fun createNumbersRow(): LinearLayout {
        val row = ArrayList<View>()
        row.add(createButton("1", columnWidth, '1'))
        row.add(createButton("2", columnWidth, '2'))
        row.add(createButton("3", columnWidth, '3'))
        row.add(createButton("4", columnWidth, '4'))
        row.add(createButton("5", columnWidth, '5'))
        row.add(createButton("6", columnWidth, '6'))
        row.add(createButton("7", columnWidth, '7'))
        row.add(createButton("8", columnWidth, '8'))
        row.add(createButton("9", columnWidth, '9'))
        row.add(createButton("0", columnWidth, '0'))
        row.add(createButton("Del", columnWidth, KeyboardController.SpecialKey.DELETE))
        return createRow(row)
    }

    private fun createCapsButton(): Button {
        val alphaText = "ABC"
        return when(symbolsState) {
            SymbolState.SYMBOLS_DISABLED -> {
                when(capsState) {
                    CapsState.CAPS_DISABLED -> {
                        createButton("⇧", columnWidth, KeyboardController.SpecialKey.CAPS)
                    }
                    CapsState.CAPS_ENABLED -> {
                        createButton("⬆", columnWidth, KeyboardController.SpecialKey.CAPS)
                    }
                    CapsState.CAPS_LOCK_ENABLED -> {
                        val button = createButton(
                                "⇧", columnWidth, KeyboardController.SpecialKey.CAPS)
                        ComponentUtils.setBackgroundTint(
                                button, Color.parseColor("#33CCFF"))
                        return button
                    }
                }
            }
            SymbolState.SYMBOL_ONE_DISPLAYED -> {
                createButton(alphaText, columnWidth, KeyboardController.SpecialKey.ALPHA)
            }
            SymbolState.SYMBOL_TWO_DISPLAYED -> {
                createButton(alphaText, columnWidth, KeyboardController.SpecialKey.ALPHA)
            }
        }
    }
}