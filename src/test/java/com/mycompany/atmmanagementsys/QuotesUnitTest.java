package com.mycompany.atmmanagementsys;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuotesUnitTest {

    @Test
    public void testReturnQuotes() {
        Random mockedRandom = Mockito.mock(Random.class);
        Quotes quotes = new Quotes(mockedRandom);

        Mockito.when(mockedRandom.nextInt(7)).thenReturn(0);
        String expectedCase0 = "To accomplish great things, we\n"
                + "must not only act, but also dream,\n"
                + "not only plan, but also believe.\n\n"
                + "- Anatole France";
        assertEquals(expectedCase0, quotes.returnQuotes());

        Mockito.when(mockedRandom.nextInt(7)).thenReturn(1);
        String expectedCase1 = "Victory is always possible for the\n"
                + "person who refuses to\n"
                + "stop fighting.\n\n"
                + "- Napoleon Hill";
        assertEquals(expectedCase1, quotes.returnQuotes());

        Mockito.when(mockedRandom.nextInt(7)).thenReturn(2);
        String expectedCase2 = "Great works are performed\n"
                + "not by strength,\n"
                + "but perseverance.\n\n"
                + "- Dr. Samuel Johnson";
        assertEquals(expectedCase2, quotes.returnQuotes());
    }
}