package tim2.CulturalHeritage.helper;

import java.util.List;

public interface MapperInterface<T,U,Z> {

    T toEntity(Z dto);

    U toDto(T entity);

    List<U> toDtoList(List<T> entityList);
}
