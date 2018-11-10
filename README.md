# Android-CustomKeyboard
Fully customizable Android keyboard written in Kotlin.

## Prerequisites
Make sure you have a version of Android Studio installed that supports Kotlin (3+ should be fine).

## Running the Demo
Just download the project, open it in Android Studio, connect to a virtual or physical device, and run it! There shouldn't be any further configuration required (unless you need to download build tools, etc., but Android Studio should prompt you to do that).

The demo is one activity (MainActivity.java). Below are GIF's of the functionality in both ladscape and portrait. Notice that in both orientations the keyboard takes up the full screen width, and the button widths change (they are a percentage of the screen width). That is because it extends the [ResizableRelativeLayout](https://github.com/DonBrody/Android-ResizableRelativeLayout). Additionally, the component responsible for the expansion and collapse of the keyboard is the [ExpandableView](https://github.com/DonBrody/Android-ExpandableView). Please take a look at their documentation for more detail.

<img height="300px" width="400px" src="https://s3.amazonaws.com/don-brody-images/CustomKeyboard+Landscape.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;<img height="400px" width="300px" src="https://s3.amazonaws.com/don-brody-images/CustomKeyboard+Portrait.gif"/>

## Why I Made It
The Android system keyboard API is limited and difficult to work with. I spent many hours researching different ways to gain full control of the keyboard, and ended up piecing together a few different approaches and adding some of my own flavor to it. I hope that I can save
somebody else a lot of time and headache.

## How It Works
The CustomKeyboardView can be injected with any keyboard layout and controller. All you need to do is create an EditText, pass it to the CustomKeyboardView, and indicate what keyboard type it should be using. Below is the entire MainActivity demo class:
```
class MainActivity : AppCompatActivity() {
    private lateinit var keyboard: CustomKeyboardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberField: EditText = findViewById(R.id.testNumberField)
        val numberDecimalField: EditText = findViewById(R.id.testNumberDecimalField)
        val qwertyField: EditText = findViewById(R.id.testQwertyField)

        keyboard = findViewById(R.id.customKeyboardView)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER, numberField)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.NUMBER_DECIMAL, numberDecimalField)
        keyboard.registerEditText(CustomKeyboardView.KeyboardType.QWERTY, qwertyField)
    }

    override fun onBackPressed() {
        if (keyboard.isExpanded) {
            keyboard.translateLayout()
        } else {
            super.onBackPressed()
        }
    }
}
```
The EditText's are stored in a map by the CustomKeyboardView:
```
private val keyboards = HashMap<EditText, KeyboardLayout?>()
```
As you can see, they are mapped to their KeyboardLayout, which stores its own controller. This process is shown below:
```
    fun registerEditText(type: KeyboardType, field: EditText) {
        field.setRawInputType(InputType.TYPE_CLASS_TEXT)
        field.setTextIsSelectable(true)
        field.showSoftInputOnFocus = false
        field.isSoundEffectsEnabled = false
        field.isLongClickable = false

        val inputConnection = field.onCreateInputConnection(EditorInfo())
        keyboards[field] = createKeyboardLayout(type, inputConnection)
        ...
    }
    
    private fun createKeyboardLayout(type: KeyboardType, ic: InputConnection): KeyboardLayout? {
        when(type) {
            KeyboardType.NUMBER -> {
                return NumberKeyboardLayout(context, createKeyboardController(type, ic))
            }
            KeyboardType.NUMBER_DECIMAL -> {
                return NumberDecimalKeyboardLayout(context, createKeyboardController(type, ic))
            }
            KeyboardType.QWERTY -> {
                return QwertyKeyboardLayout(context, createKeyboardController(type, ic))
            }
            else -> return@createKeyboardLayout null // this should never happen
        }
    }

    private fun createKeyboardController(type: KeyboardType, ic: InputConnection): KeyboardController? {
        when(type) {
            KeyboardType.NUMBER -> {
                return DefaultKeyboardController(ic)
            }
            KeyboardType.NUMBER_DECIMAL -> {
                return NumberDecimalKeyboardController(ic)
            }
            KeyboardType.QWERTY -> {
                return DefaultKeyboardController(ic)
            }
            else -> return@createKeyboardController null // this should never happen
        }
    }
```
You might also notice that the CustomKeyboardView is currently using some very basic controllers, so why would we separate the controller logic from the layout? Because more complicated controllers may be needed in the future. This architecture allows for more complex keyboard layouts to be created. For example, what if we need to create a keyboard that handles latitudes. That can get pretty complicated. Not only do we have to consider that latitudes can only span between S 90.0000 and N 90.0000 degrees, but what if we want to represent those values in degrees and minutes, or degrees and minutes and seconds, or whatever format the user chooses? The architecture might be a little overkill for simple keyboards, but it leaves open the possibility to create any keyboard we may need to in the future without any significant changes to the architecture.

## Updates
The CustomKeyboardView is now capable of auto registering all EditText's within a ViewGroup. Just pass any ViewGroup to the autoRegisterEditTexts method of any CustomKeyboardView instance, and it will recursively search the View tree for EditText's, check their type, and automatically bind to their InputConnection's.

Additionally, a CustomTextField component has been added. This component is a simple extension of the Android EditText component. It auto sets simple settings such as background color, max characters, and text size. The most important addition to this extension is the keyboard type property. You can create this component programmaticaly, set it's type, and pass it's containing ViewGroup to the CustomKeyboardView to auto bind to it's InputConnection. This allows you to create keyboard types that are not recognized by Android, set the keyboard type of this new component, and have that type auto recognized by the CustomKeyboardView. 
Note: The CustomTextField is a very simple component, and like the custom keyboard's in this repository, it's expected that you'll override their attributes to fit your project's needs.

## Next Steps
Add the CustomKeyboardView to any (and hopefully all :) of your projects, add any layout or controllers you'd like, modify the UI in any way that fits your needs, and enjoy!

## Dependencies
* [ResizableRelativeLayout](https://github.com/DonBrody/Android-ResizableRelativeLayout)
* [ExpandableView](https://github.com/DonBrody/Android-ExpandableView)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
