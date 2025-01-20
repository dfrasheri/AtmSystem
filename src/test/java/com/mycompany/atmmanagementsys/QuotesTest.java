package com.mycompany.atmmanagementsys;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuotesTest {

    @Test
    public void testReturnQuoteCase0() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(0);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "To accomplish great things, we\n"
                        + "must not only act, but also dream,\n"
                        + "not only plan, but also believe.\n\n"
                        + "- Anatole France";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase1() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(1);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Victory is always possible for the\n"
                        + "person who refuses to\n"
                        + "stop fighting.\n\n"
                        + "- Napoleon Hill";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase2() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(2);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Great works are performed\n"
                        + "not by strength,\n"
                        + "but perseverance.\n\n"
                        + "- Dr. Samuel Johnson";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase3() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(3);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Society may predict, but only you\n"
                        + "can determine your destiny.\n\n"
                        + "- Clair Oliver";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase4() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(4);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Success comes from having dreams\n"
                        + "that are bigger than your fears.\n\n"
                        + "- Terry Litwiller";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase5() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(5);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Imagination is more important\n"
                        + "than knowledge.\n\n"
                        + "- Albert Einstein";
        assertEquals(expected, quotes.returnQuotes());
    }

    @Test
    public void testReturnQuoteCase6() {
        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(7)).thenReturn(6);

        Quotes quotes = new Quotes(mockedRandom);
        String expected = "Without a goal, discipline is\n"
                        + "nothing but self-punishment.\n\n"
                        + "- Elanor Roosevelt";
        assertEquals(expected, quotes.returnQuotes());
    }
}