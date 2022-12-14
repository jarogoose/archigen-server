package org.jarogoose.archigen.core.template;

import static org.assertj.core.api.Assertions.assertThat;

public class Then {

  public static void validTemplate(String actual, String expected) {
    String[] expectLines = expected.split(System.lineSeparator());
    String[] actualLines = actual.split(System.lineSeparator());

    for (int i = 0; i < expectLines.length; i++) {
      assertThat(actualLines[i])
          .withFailMessage("Line %s expect to be \n %s \n but was \n %s", i + 1, expectLines[i], actualLines[i])
          .isEqualTo(expectLines[i]);
    }

    assertThat(actualLines.length).isEqualTo(expectLines.length);
  }

}
