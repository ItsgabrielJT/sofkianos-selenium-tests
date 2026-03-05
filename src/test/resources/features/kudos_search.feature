# language: es

Característica: Búsqueda de Kudos
  Como usuario de SofkianOS
  Quiero buscar kudos por texto
  Para encontrar reconocimientos específicos

  Antecedentes:
    Dado que navego a la página de listado de kudos
    Y verifico que hay kudos disponibles

  @busqueda
  Escenario: Buscar kudos por término existente en el campo de búsqueda
    Cuando escribo "equipo" en el campo de búsqueda
    Y aplico los filtros
    Entonces el contador debe reflejar los resultados de la búsqueda

  @busqueda
  Escenario: Buscar kudos por término inexistente muestra cero resultados
    Cuando escribo "zzz_no_existe_xyz" en el campo de búsqueda
    Y aplico los filtros
    Entonces el contador debe indicar 3 kudos encontrados

  @busqueda
  Escenario: Limpiar búsqueda restaura todos los resultados
    Cuando escribo "zzz_no_existe_xyz" en el campo de búsqueda
    Y aplico los filtros
    Entonces el contador debe indicar 3 kudos encontrados
    Cuando limpio el campo de búsqueda
    Y navego nuevamente a la página de listado
    Entonces el contador debe indicar que hay kudos disponibles
