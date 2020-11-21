package tim2.CulturalHeritage.service;

import java.util.List;

import tim2.CulturalHeritage.model.CHType;

public interface CHTypeService {

    public List<CHType> findAll();

    public CHType findById(Long id);

    public CHType add(CHType chType);

    public CHType update(CHType chType);

    public void deleteById(Long id);
}
