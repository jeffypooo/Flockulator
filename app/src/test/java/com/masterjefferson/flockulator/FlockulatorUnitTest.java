package com.masterjefferson.flockulator;

import com.masterjefferson.flockulator.app.timber.UnitTestTree;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import timber.log.Timber;

/**
 * ${FILE_NAME}
 * Created by jeff on 9/21/17.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class FlockulatorUnitTest {

  @Before
  public void setup() {
    Timber.uprootAll();
    Timber.plant(new UnitTestTree());
  }

}
