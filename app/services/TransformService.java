package services;

import java.util.List;
import java.util.Set;

/**
 * Created by Dayel Ostraco
 * 10/2/15
 */
public interface TransformService {

    <F, T> T map(F mapFromObject, Class mapToClass);

    <T> T mapList(List<?> mapFromList, Class mapToClass);

    <T> T mapSet(Set<?> mapFromSet, Class mapToClass);
}
