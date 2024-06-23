package com.moaaz.modernhome.generic;

import java.util.List;

public interface CrudService<Request, Entity, Response, ID> {


    Response add(Request request);

    Response update(Request request, ID id);

    void delete(ID id);

    List<Response> getAll();

    Entity getById(ID id);

    Response getResponseById(ID Id);
}
