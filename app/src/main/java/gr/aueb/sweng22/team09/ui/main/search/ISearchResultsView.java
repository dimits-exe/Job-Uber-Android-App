package gr.aueb.sweng22.team09.ui.main.search;

/**
 * An adapter that facilitates communication from a {@link SearchPresenter} to a
 * {@link SearchFragment}.
 *
 * @author Dimitris Tsirmpas
 */
interface ISearchResultsView {

    /**
     * Displays an error on a field in the Search Form.
     *
     * @param field        the field in which the error occurred
     * @param errorMessage the error message
     */
    void showErrorOn(SearchFormField field, String errorMessage);
}
