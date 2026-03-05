# Contributing to SofkianOS Selenium Tests

¡Gracias por tu interés en contribuir! 🎉

## Cómo Contribuir

1. **Fork** el repositorio
2. Crea una **rama** para tu feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** tus cambios siguiendo las convenciones de commit
4. **Push** a la rama (`git push origin feature/AmazingFeature`)
5. Abre un **Pull Request**

## Convenciones de Código

### Java
- Usar Java 17
- Seguir las convenciones de código de Java
- Documentar métodos públicos con JavaDoc
- Nombres de métodos en camelCase
- Nombres de clases en PascalCase

### Gherkin
- Escribir features en español
- Usar verbos específicos: Dado, Cuando, Entonces
- Mantener escenarios concisos y claros
- Usar tags apropiados (@smoke, @listado, @busqueda, etc.)

### Page Objects
- Un Page Object por página/componente
- Localizadores privados y finales
- Métodos públicos con nombres descriptivos
- Retornar `this` para permitir method chaining
- Preferir `By.id()` sobre otros localizadores cuando sea posible

### Step Definitions
- Un archivo de steps por funcionalidad
- Métodos pequeños que delegan a Page Objects
- No incluir lógica de negocio en los steps
- Usar AssertJ para las aserciones

## Tests

Antes de enviar tu PR, asegúrate de que:
- ✅ Todos los tests pasan (`gradle clean test`)
- ✅ No hay errores de compilación
- ✅ El código está formateado correctamente
- ✅ Has agregado tests para tu nueva funcionalidad

## Reportar Issues

Si encuentras un bug o tienes una sugerencia:
1. Verifica que no exista un issue similar
2. Abre un nuevo issue con una descripción clara
3. Incluye pasos para reproducir el problema
4. Incluye la versión de Java, Gradle y navegador que estás usando

## Preguntas

Si tienes preguntas, no dudes en abrir un issue con la etiqueta `question`.
