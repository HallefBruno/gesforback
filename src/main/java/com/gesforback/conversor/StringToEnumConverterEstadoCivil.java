
package com.gesforback.conversor;

import com.gesforback.entity.EstadoCivil;
import org.springframework.core.convert.converter.Converter;


public class StringToEnumConverterEstadoCivil implements Converter<String, EstadoCivil> {
    @Override
    public EstadoCivil convert(String source) {
        try {
            return EstadoCivil.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}