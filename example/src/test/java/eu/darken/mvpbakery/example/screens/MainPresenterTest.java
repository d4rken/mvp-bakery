package eu.darken.mvpbakery.example.screens;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock MainPresenter.View view;

    @Test
    public void testCount() throws Throwable {
        MainPresenter mainPresenter = new MainPresenter(null);
        verify(view, never()).showBinderCounter(anyInt());
        mainPresenter.onBindChange(view);
        verify(view).showBinderCounter(1);
        mainPresenter.onBindChange(null);
        mainPresenter.onBindChange(view);
        verify(view).showBinderCounter(2);
    }
}
