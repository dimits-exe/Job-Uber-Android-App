package gr.aueb.sweng22.team09.ui.util;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A wrapper for a Field-TextInputLayout map.
 *
 * @author Alex Mandelias
 */
public class FieldManager<T extends Enum<T>> {

    private final HashMap<T, TextInputLayout> fieldToInputMap = new HashMap<>();

    /**
     * Adds a Field-TextInputLayout pair to this Manager. Adding another pair with the same Field
     * will override the previous one.
     *
     * @param field the Field to add
     * @param til   the TextInputLayout associated with that Field
     * @throws NullPointerException if the TextInputLayout is {@code null}
     */
    public void add(T field, TextInputLayout til) {
        if (til == null)
            throw new NullPointerException("The TextInputLayout cannot be null");

        fieldToInputMap.put(field, til);
    }

    /**
     * Returns the Text of the TextInputLayout associated with a Field.
     *
     * @param field the Field
     * @return the text on that Field
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     * @throws NullPointerException   if the TextInputLayout does not contain an EditText
     */
    public String get(T field) {
        return getEditText(field).getText().toString();
    }

    /**
     * Sets the Text of the TextInputLayout associated with a Field.
     *
     * @param field the Field
     * @param text  the text to set
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     * @throws NullPointerException   if the TextInputLayout does not contain an EditText
     */
    public void set(T field, String text) {
        getEditText(field).setText(text);
    }

    /**
     * Displays an error on the TextInputLayout associated with a Field.
     *
     * @param field        the Field
     * @param errorMessage the error message to display
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     * @throws NullPointerException   if the TextInputLayout does not contain an EditText
     */
    public void showError(T field, String errorMessage) {
        getTextInputLayout(field).setError(errorMessage);
    }

    /**
     * Clears the error on the TextInputLayout associated with a Field.
     *
     * @param field the Field
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     * @throws NullPointerException   if the TextInputLayout does not contain an EditText
     */
    public void clearError(T field) {
        getTextInputLayout(field).setError(null);
    }

    /**
     * Returns the TextInputLayout associated with a Field.
     *
     * @param field the Field
     * @return the EditText of the TextInputLayout of the Field
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     */
    private TextInputLayout getTextInputLayout(T field) {
        if (!fieldToInputMap.containsKey(field))
            throw new NoSuchElementException("Field " + field + " doesn't have a TextInputLayout");

        return fieldToInputMap.get(field);
    }

    /**
     * Returns the EditText associated with a Field.
     *
     * @param field the Field
     * @return the EditText of the TextInputLayout of the Field
     * @throws NoSuchElementException if no TextInputLayout is associated with that Field
     * @throws NullPointerException   if the TextInputLayout does not contain an EditText
     */
    private EditText getEditText(T field) {
        EditText et = getTextInputLayout(field).getEditText();
        if (et == null)
            throw new NullPointerException("Field " + field
                    + " has a TextInputLayout but not an EditText");

        return et;
    }
}
