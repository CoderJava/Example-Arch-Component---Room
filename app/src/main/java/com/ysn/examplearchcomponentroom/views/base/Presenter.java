package com.ysn.examplearchcomponentroom.views.base;

/**
 * Created by root on 01/07/17.
 */

public interface Presenter<T extends View>{

    void onAttach(T view);

    void onDetach();

}
