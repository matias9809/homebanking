package com.mindhub.homebanking.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class UtilsTest {
    @Test
    public void numberRandomCard(){
        int number1=(int) (Math.random() * (999 - 100) + 100);
        assertThat(number1, is(lessThan(999)));
        assertThat(number1, is(greaterThan(100)));

    }
    @Test
    public void numberAccount(){
        int number1=(int) (Math.random() * (99999999));
        assertThat(number1, is(lessThan(99999999)));
        assertThat(number1, is(greaterThan(1)));
    }
}
