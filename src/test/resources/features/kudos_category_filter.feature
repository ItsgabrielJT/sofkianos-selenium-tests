# language: es

Característica: Filtrado por Categoría de Kudos
  Como usuario de SofkianOS
  Quiero filtrar kudos por categoría
  Para ver reconocimientos de un tipo específico

  Antecedentes:
    Dado que navego a la página de listado de kudos

  @categoria
  Escenario: Verificar que el dropdown de categoría contiene las opciones esperadas
    Entonces el dropdown de categoría debe contener las opciones:
      | Todas las categorías |
      | Innovation           |
      | Teamwork             |
      | Passion              |
      | Mastery              |

  @categoria
  Escenario: La categoría por defecto es "Todas las categorías"
    Entonces la categoría seleccionada debe ser "Todas las categorías"

  @categoria
  Esquema del escenario: Filtrar por categoría específica
    Cuando selecciono la categoría "<categoria>"
    Y aplico los filtros
    Entonces el contador debe reflejar los resultados de la búsqueda

    Ejemplos:
      | categoria  |
      | Innovation |
      | Teamwork   |
      | Passion    |
      | Mastery    |

  @categoria
  Escenario: Volver a "Todas las categorías" muestra todos los kudos
    Cuando selecciono la categoría "Teamwork"
    Y aplico los filtros
    Y navego nuevamente a la página de listado
    Entonces la categoría seleccionada debe ser "Todas las categorías"
    Y el contador debe indicar que hay kudos disponibles
