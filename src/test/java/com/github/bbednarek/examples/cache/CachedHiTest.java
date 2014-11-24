package com.github.bbednarek.examples.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachedHiTest {

    private CachedHi cachedHi;
    private String name;

    @Mock
    private Hi delegate;

    @Before
    public void setUp() throws Exception {
        name = "John";
        when(delegate.hi(name)).thenReturn(Observable.just("Hi " + name));
        cachedHi = new CachedHi(delegate);
    }

    @Test
    public void testHi() throws Exception {
        assertEquals("Hi " + name, cachedHi.hi(name).toBlocking().single());
        verify(delegate, times(1)).hi(name);
        assertEquals("Hi " + name, cachedHi.hi(name).toBlocking().single());
        verify(delegate, times(1)).hi(name);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    public void testHi_Subscriber() throws Exception {
        TestSubscriber<String> ts = new TestSubscriber<>();
        cachedHi.hi(name).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Hi " + name));
        verify(delegate, times(1)).hi(name);

        ts = new TestSubscriber<>();
        cachedHi.hi(name).subscribe(ts);
        ts.awaitTerminalEvent();
        ts.assertNoErrors();
        ts.assertReceivedOnNext(Arrays.asList("Hi " + name));
        verify(delegate, times(1)).hi(name);
        verifyNoMoreInteractions(delegate);
    }
}
