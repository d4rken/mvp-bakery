package eu.darken.mvpbakery.example.screens;

import android.app.LoaderManager;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class PresenterLoaderFactoryTest {
    @Mock Context context;
    @Mock LoaderManager loaderManager;

    @Test
    public void testLoad() {
        assertThat(true, is(true));
    }
}
