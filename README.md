# 🧪 Selenium Test Suite — SofkianOS MVP

## Índice

- [1. Descripción del Proyecto](#1-descripción-del-proyecto)
- [2. Arquitectura y Estructura del Proyecto](#2-arquitectura-y-estructura-del-proyecto)
- [3. Web Elements y Localizadores](#3-web-elements-y-localizadores)
  - [3.1 ¿Qué son?](#31-qué-son)
  - [3.2 Tipos de Localizadores](#32-tipos-de-localizadores)
  - [3.3 Jerarquía de Selección](#33-jerarquía-de-selección)
  - [3.4 Ejemplos Prácticos en el Proyecto](#34-ejemplos-prácticos-en-el-proyecto)
  - [3.5 XPaths Complejos con ayuda de IA](#35-xpaths-complejos-con-ayuda-de-ia)
  - [3.6 Cuándo usar cada Localizador](#36-cuándo-usar-cada-localizador)
- [4. Page Object Model](#4-page-object-model)
- [5. Cucumber y Gherkin](#5-cucumber-y-gherkin)
- [6. Flujo Completo: Del Feature al Localizador](#6-flujo-completo-del-feature-al-localizador)
- [7. Ejecución](#7-ejecución)

---

## 1. Descripción del Proyecto

Proyecto de pruebas automatizadas con **Selenium WebDriver**, **Cucumber** y **Java** para la aplicación **SofkianOS MVP** (`0.0.0.0:5173`). Cubre los siguientes flujos en la sección **Explorar Kudos** (`/kudos/list`):

| Flujo | Escenarios |
|---|---|
| Listado de Kudos | Verificar tabla, columnas, datos visibles |
| Búsqueda por texto | Filtrar por mensaje, remitente o destinatario |
| Filtrado por Categoría | Dropdown con Innovation, Teamwork, Passion, Mastery |
| Filtrado por Fecha | Campos desde/hasta y botón Aplicar Filtros |

---

## 2. Arquitectura y Estructura del Proyecto

```
selenium/
├── build.gradle
├── settings.gradle
└── src/
    └── test/
        ├── java/com/sofkianos/
        │   ├── config/
        │   │   └── TestConfig.java              # URLs, timeouts
        │   ├── driver/
        │   │   └── DriverManager.java            # ThreadLocal WebDriver
        │   ├── pages/                            # 📌 PAGE OBJECTS (localizadores aquí)
        │   │   ├── BasePage.java                 # Métodos comunes (waits, clicks)
        │   │   ├── LandingPage.java              # Página principal
        │   │   └── KudosListPage.java            # Página de listado de kudos
        │   ├── steps/                            # Step Definitions de Cucumber
        │   │   ├── KudosListSteps.java
        │   │   ├── KudosSearchSteps.java
        │   │   ├── KudosCategorySteps.java
        │   │   └── KudosDateFilterSteps.java
        │   ├── hooks/
        │   │   └── Hooks.java                    # @Before / @After
        │   └── runners/
        │       └── CucumberTestRunner.java       # JUnit 5 Runner
        └── resources/
            └── features/                         # Archivos .feature (Gherkin)
                ├── kudos_listing.feature
                ├── kudos_search.feature
                ├── kudos_category_filter.feature
                └── kudos_date_filter.feature
```

**Capas del proyecto:**

```
┌───────────────────────────────────────────────────┐
│                 Features (.feature)                │  ← Qué probar (lenguaje natural)
├───────────────────────────────────────────────────┤
│               Steps (StepDefinitions)              │  ← Orquesta las acciones
├───────────────────────────────────────────────────┤
│              Pages (Page Objects)                   │  ← Localizadores + métodos de interacción
├───────────────────────────────────────────────────┤
│          Driver / Config / Hooks                    │  ← Infraestructura
├───────────────────────────────────────────────────┤
│              Selenium WebDriver                     │  ← Controla el navegador
└───────────────────────────────────────────────────┘
```

---

## 3. Web Elements y Localizadores

### 3.1 ¿Qué son?

- **WebElement**: Es la representación en Java de un elemento HTML del DOM del navegador. Permite interactuar con él (click, escribir texto, leer contenido, etc.).
- **Localizador (By)**: Es la estrategia/mecanismo que Selenium usa para **encontrar** ese WebElement dentro del DOM.

```java
// Localizador: define CÓMO encontrar el elemento
By localizador = By.id("search-input");

// WebElement: el elemento encontrado en el DOM
WebElement elemento = driver.findElement(localizador);

// Interacción con el WebElement
elemento.sendKeys("Innovation");
elemento.click();
String texto = elemento.getText();
```

### 3.2 Tipos de Localizadores

Selenium ofrece **8 estrategias** de localización:

| # | Estrategia | Sintaxis Java | Busca por |
|---|---|---|---|
| 1 | **ID** | `By.id("search-input")` | Atributo `id` del HTML |
| 2 | **Name** | `By.name("endDate")` | Atributo `name` del HTML |
| 3 | **CSS Selector** | `By.cssSelector(".filters button")` | Selector CSS |
| 4 | **XPath** | `By.xpath("//table/tbody/tr")` | Expresión XPath |
| 5 | **Class Name** | `By.className("kudos-counter")` | Atributo `class` |
| 6 | **Tag Name** | `By.tagName("option")` | Etiqueta HTML |
| 7 | **Link Text** | `By.linkText("Explorar Kudos")` | Texto exacto de `<a>` |
| 8 | **Partial Link Text** | `By.partialLinkText("Explorar")` | Texto parcial de `<a>` |

### 3.3 Jerarquía de Selección

> **Regla fundamental:** Siempre elegir el localizador más simple, estable y rápido posible.

```
Jerarquía de prioridad (de más recomendado a menos):

1. 🏆 By.id()              → Más rápido, único por diseño
2. 🥈 By.name()            → Estable en formularios
3. 🥉 By.cssSelector()     → Potente, legible, rápido
4. 🔧 By.xpath()           → Más flexible, más complejo y lento
5. 📎 By.className()       → Útil si la clase es única
6. 📎 By.tagName()         → Muy genérico, poco recomendado solo
7. 📎 By.linkText()        → Solo para enlaces <a>
8. 📎 By.partialLinkText() → Coincidencia parcial en enlaces
```

**Árbol de decisión:**

```
¿Tiene id?
  ├─ Sí → By.id() ✅
  └─ No
      ¿Tiene data-testid?
        ├─ Sí → By.cssSelector("[data-testid='...']") ✅
        └─ No
            ¿Tiene name único?
              ├─ Sí → By.name() ✅
              └─ No
                  ¿Se puede resolver con CSS?
                    ├─ Sí → By.cssSelector() ✅
                    └─ No (necesito texto, posición, ejes)
                        └─ By.xpath() ✅
```

### 3.4 Ejemplos Prácticos en el Proyecto

Dado el siguiente HTML de la aplicación SofkianOS:

```html
<div class="kudos-list-container" id="kudos-section">
  <div class="filters">
    <input id="search-input" type="text" name="query" class="search-box"
           placeholder="Buscar por mensaje, remitente o destinatario..." />
    <select id="category-select" class="category-dropdown" name="category">
      <option value="">Todas las categorías</option>
      <option value="Innovation">Innovation</option>
      <option value="Teamwork">Teamwork</option>
      <option value="Passion">Passion</option>
      <option value="Mastery">Mastery</option>
    </select>
    <input type="date" name="startDate" class="date-filter" />
    <input type="date" name="endDate" class="date-filter" />
    <button class="apply-btn" data-testid="apply-filters">Aplicar Filtros</button>
  </div>
  <span class="kudos-counter">Mostrando 3 kudos</span>
  <button class="sort-btn">Más recientes</button>
  <table class="kudos-table">
    <thead>
      <tr><th>De</th><th>Para</th><th>Categoría</th><th>Mensaje</th><th>Fecha</th></tr>
    </thead>
    <tbody>
      <tr><td>Ana</td><td>Carlos</td><td>Innovation</td><td>Gran idea</td><td>2026-01-15</td></tr>
      <tr><td>Luis</td><td>María</td><td>Teamwork</td><td>Excelente apoyo</td><td>2026-02-20</td></tr>
      <tr><td>Pedro</td><td>Ana</td><td>Passion</td><td>Dedicación total</td><td>2026-02-28</td></tr>
    </tbody>
  </table>
</div>
```

#### Localizador por ID — El más rápido y confiable

```java
// HTML: <input id="search-input" type="text" ... />
private final By searchInput = By.id("search-input");

// HTML: <select id="category-select" ...>
private final By categoryDropdown = By.id("category-select");
```

> ✅ **¿Por qué?** El `id` es único en toda la página, la búsqueda es directa y rapidísima.

#### Localizador por Name

```java
// HTML: <input type="date" name="startDate" ... />
private final By startDateInput = By.name("startDate");

// HTML: <input type="date" name="endDate" ... />
private final By endDateInput = By.name("endDate");
```

> ✅ **¿Por qué?** No tienen `id`, pero el atributo `name` es único y estable.

#### Localizador por CSS Selector — Potente y legible

```java
// Por clase
// HTML: <span class="kudos-counter">Mostrando 3 kudos</span>
private final By kudosCounter = By.cssSelector(".kudos-counter");

// Por atributo data-testid (recomendado para testing)
// HTML: <button data-testid="apply-filters">Aplicar Filtros</button>
private final By applyFiltersButton = By.cssSelector("[data-testid='apply-filters']");

// Combinación clase + tipo
// HTML: <input type="date" class="date-filter" ... />
private final By dateFilters = By.cssSelector("input.date-filter[type='date']");

// Jerarquía padre > hijo
// HTML: <table class="kudos-table"><tbody><tr>...
private final By tableRows = By.cssSelector(".kudos-table tbody tr");

// Pseudo-selectores
private final By firstRow = By.cssSelector(".kudos-table tbody tr:first-child");
private final By lastRow = By.cssSelector(".kudos-table tbody tr:last-child");
```

> ✅ **¿Por qué?** CSS es rápido, legible y cubre el 80% de los casos donde no hay `id`.

#### Localizador por XPath — Para casos complejos

```java
// Buscar por texto visible del botón
private final By sortButton = By.xpath(
    "//button[contains(text(),'recientes') or contains(text(),'antiguos')]"
);

// Posición: 3ra columna de cada fila
private final By categoryColumns = By.xpath("//table/tbody/tr/td[3]");

// Condición: fila que contenga 'Innovation' en la categoría
private final By innovationRows = By.xpath(
    "//table/tbody/tr[td[3][normalize-space()='Innovation']]"
);
```

> ✅ **¿Por qué?** XPath es necesario cuando buscamos por texto visible, posición o relaciones complejas entre nodos.

#### Localizador por Class Name

```java
// HTML: <span class="kudos-counter">...</span>
private final By counter = By.className("kudos-counter");
```

> ⚠️ **Limitación:** Solo funciona con una clase única. No admite `.clase1.clase2`.

#### Localizador por Link Text

```java
// HTML: <a href="/kudos/list">Explorar Kudos</a>
private final By exploreLink = By.linkText("Explorar Kudos");

// Coincidencia parcial
private final By exploreLinkPartial = By.partialLinkText("Explorar");
```

> ⚠️ **Limitación:** Solo funciona con elementos `<a>` (enlaces).

#### Tabla resumen aplicada al HTML del proyecto

```
┌──────────────────────┬──────────────────────────────────────────────────┬────────────────┐
│ Elemento HTML        │ Localizador elegido                              │ Razón          │
├──────────────────────┼──────────────────────────────────────────────────┼────────────────┤
│ Campo búsqueda       │ By.id("search-input")                            │ Tiene id       │
│ Dropdown categoría   │ By.id("category-select")                         │ Tiene id       │
│ Fecha inicio         │ By.name("startDate")                             │ name único     │
│ Fecha fin            │ By.name("endDate")                               │ name único     │
│ Botón Aplicar        │ By.cssSelector("[data-testid='apply-filters']")  │ data-testid    │
│ Contador             │ By.cssSelector(".kudos-counter")                 │ clase única    │
│ Botón ordenamiento   │ By.xpath("//button[contains(text(),'recientes')]")│ texto dinámico│
│ Filas de tabla       │ By.cssSelector(".kudos-table tbody tr")          │ jerarquía CSS  │
│ Columna categoría    │ By.xpath("//table/tbody/tr/td[3]")               │ posición       │
│ Fila específica      │ By.xpath("//tr[td[contains(.,'Innovation')]]")   │ condición texto│
│ Link Explorar Kudos  │ By.linkText("Explorar Kudos")                    │ es un <a>      │
└──────────────────────┴──────────────────────────────────────────────────┴────────────────┘
```

### 3.5 XPaths Complejos con ayuda de IA

La IA es especialmente útil para generar XPaths complejos. Aquí los patrones más comunes:

#### Caso 1: Buscar fila por contenido de múltiples celdas

**Prompt a la IA:**
> *"Necesito un XPath para encontrar la fila de tabla donde la columna 'De' es 'Ana' Y la columna 'Categoría' es 'Innovation'"*

```java
// Resultado generado:
private final By specificRow = By.xpath(
    "//table/tbody/tr[td[1][normalize-space()='Ana'] and td[3][normalize-space()='Innovation']]"
);
```

**Explicación del XPath:**
```
//table/tbody/tr                          → Todas las filas del tbody
  [                                       → Filtro: solo las que cumplan...
    td[1][normalize-space()='Ana']        →   1ra celda = 'Ana'
    and                                   →   Y además
    td[3][normalize-space()='Innovation'] →   3ra celda = 'Innovation'
  ]
```

#### Caso 2: Ejes XPath (relaciones entre nodos)

**Prompt a la IA:**
> *"Necesito el XPath del botón que está después del input de búsqueda"*

```java
// following-sibling: busca hermanos posteriores en el mismo nivel
private final By buttonAfterSearch = By.xpath(
    "//input[@id='search-input']/following-sibling::button[1]"
);

// ancestor: sube al elemento padre/abuelo
// "Desde la celda 'Innovation', sube a su fila <tr>"
private final By rowFromCell = By.xpath(
    "//td[text()='Innovation']/ancestor::tr"
);

// preceding-sibling: hermano anterior
// "El label que está antes del input de fecha"
private final By labelBeforeDate = By.xpath(
    "//input[@name='startDate']/preceding-sibling::label[1]"
);

// parent: padre directo
private final By parentDiv = By.xpath(
    "//input[@id='search-input']/parent::div"
);
```

**Resumen de ejes XPath:**
```
                    ancestor::div
                         ↑
preceding-sibling::label ← [ELEMENTO] → following-sibling::button
                         ↓
                    descendant::span
```

#### Caso 3: Funciones de texto en XPath

**Prompt a la IA:**
> *"Necesito encontrar todos los kudos cuyo mensaje contenga 'excelente' sin importar mayúsculas"*

```java
// contains(): coincidencia parcial
private final By partialMatch = By.xpath(
    "//td[contains(text(),'excelente')]"
);

// translate() para búsqueda case-insensitive (XPath 1.0)
private final By caseInsensitive = By.xpath(
    "//table/tbody/tr[td[4][contains(" +
    "translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," +
    "'excelente')]]"
);

// starts-with(): texto que empiece con...
private final By startsWithGran = By.xpath(
    "//td[starts-with(normalize-space(),'Gran')]"
);

// normalize-space(): elimina espacios extra
private final By normalized = By.xpath(
    "//td[normalize-space()='Innovation']"
);

// not(): negación
private final By notInnovation = By.xpath(
    "//table/tbody/tr[not(td[3][normalize-space()='Innovation'])]"
);
```

#### Caso 4: XPath dinámico parametrizado en el Page Object

**Prompt a la IA:**
> *"Necesito un método que reciba una categoría y devuelva las filas filtradas por esa categoría"*

```java
// En KudosListPage.java:

public List<WebElement> getRowsByCategory(String category) {
    By dynamicLocator = By.xpath(
        String.format("//table/tbody/tr[td[3][normalize-space()='%s']]", category)
    );
    return driver.findElements(dynamicLocator);
}

public WebElement getRowBySenderAndReceiver(String sender, String receiver) {
    By dynamicLocator = By.xpath(
        String.format(
            "//table/tbody/tr[td[1][normalize-space()='%s'] and td[2][normalize-space()='%s']]",
            sender, receiver
        )
    );
    return driver.findElement(dynamicLocator);
}

public boolean isKudoVisible(String sender, String receiver, String category) {
    By dynamicLocator = By.xpath(
        String.format(
            "//table/tbody/tr[td[1][normalize-space()='%s'] and " +
            "td[2][normalize-space()='%s'] and td[3][normalize-space()='%s']]",
            sender, receiver, category
        )
    );
    return !driver.findElements(dynamicLocator).isEmpty();
}
```

### 3.6 Cuándo usar cada Localizador

| Localizador | Velocidad | Legibilidad | Complejidad | Estabilidad | Caso de uso ideal |
|---|---|---|---|---|---|
| `By.id()` | ⚡⚡⚡ | ⭐⭐⭐ | Mínima | 🟢 Alta | Elemento con `id` único |
| `By.name()` | ⚡⚡⚡ | ⭐⭐⭐ | Mínima | 🟢 Alta | Campos de formulario |
| `By.cssSelector()` | ⚡⚡ | ⭐⭐ | Media | 🟡 Media | Combinaciones de atributos, clases |
| `By.xpath()` | ⚡ | ⭐ | Alta | 🟡 Media | Texto, posición, ejes, condiciones |
| `By.className()` | ⚡⚡⚡ | ⭐⭐⭐ | Mínima | 🟡 Media | Clase CSS única y descriptiva |
| `By.tagName()` | ⚡⚡⚡ | ⭐⭐ | Mínima | 🔴 Baja | Muy genérico, combinar con otros |
| `By.linkText()` | ⚡⚡ | ⭐⭐⭐ | Mínima | 🟡 Media | Enlaces `<a>` con texto fijo |
| `By.partialLinkText()` | ⚡⚡ | ⭐⭐ | Mínima | 🔴 Baja | Enlaces con texto largo/dinámico |

> 💡 **Mejor práctica:** Pide a los desarrolladores que agreguen atributos `data-testid` a los elementos clave de la UI. Así usas `By.cssSelector("[data-testid='mi-elemento']")` que es **inmune a cambios de diseño visual**.

---

## 4. Page Object Model

### ¿Qué es este patrón?

Es un patrón de diseño que crea una **clase Java por cada página** de la aplicación. Cada clase encapsula:
- Los **localizadores** de los elementos de esa página
- Los **métodos** para interactuar con esos elementos

### ¿Por qué usar este patrón?

```
SIN patrón (frágil):                      CON patrón (mantenible):
─────────────────                          ────────────────────
@When("busco kudos")                       @When("busco kudos")
public void busco() {                      public void busco() {
  driver.findElement(                        kudosListPage.searchKudos("texto");
    By.id("search-input")                 }
  ).sendKeys("texto");
  driver.findElement(                      // Si cambia el id, solo se modifica
    By.cssSelector(".btn")                 // en KudosListPage.java
  ).click();
}
// Si cambia el id, HAY QUE CAMBIAR
// EN TODOS los steps que lo usen 😱
```

### Estructura del patrón en el proyecto

```java
// BasePage.java — Métodos comunes reutilizables
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected void click(By locator) { ... }
    protected void type(By locator, String text) { ... }
    protected String getText(By locator) { ... }
    protected void waitForVisible(By locator) { ... }
}

// KudosListPage.java — Hereda de BasePage
public class KudosListPage extends BasePage {
    // LOCALIZADORES (privados, encapsulados)
    private final By searchInput = By.id("search-input");
    private final By categoryDropdown = By.id("category-select");

    // MÉTODOS PÚBLICOS (acciones de negocio)
    public void searchKudos(String text) {
        type(searchInput, text);
    }
    public void selectCategory(String category) {
        new Select(find(categoryDropdown)).selectByVisibleText(category);
    }
}
```

---

## 5. Cucumber y Gherkin

### Flujo Feature → Steps → Page Object

Ejemplo con el feature de filtrado por categoría:

```gherkin
# kudos_category_filter.feature
Escenario: Filtrar por categoría específica
  Cuando selecciono la categoría "Innovation"
  Y aplico los filtros
  Entonces el contador debe reflejar los resultados
```

```java
// KudosCategorySteps.java (Step Definition)
@Cuando("selecciono la categoría {string}")
public void selectCategory(String category) {
    kudosListPage.selectCategory(category);  // Delega al Page Object
}

@Cuando("aplico los filtros")
public void applyFilters() {
    kudosListPage.clickApplyFilters();        // Delega al Page Object
}

@Entonces("el contador debe reflejar los resultados")
public void verifyCounter() {
    String counter = kudosListPage.getCounterText();  // Delega al Page Object
    assertNotNull(counter);
}
```

```java
// KudosListPage.java (Page Object)
public void selectCategory(String category) {
    new Select(find(categoryDropdown)).selectByVisibleText(category);
    // categoryDropdown = By.id("category-select")  ← LOCALIZADOR
}

public void clickApplyFilters() {
    click(applyFiltersButton);
    // applyFiltersButton = By.cssSelector("[data-testid='apply-filters']")  ← LOCALIZADOR
}

public String getCounterText() {
    return getText(kudosCounter);
    // kudosCounter = By.cssSelector(".kudos-counter")  ← LOCALIZADOR
}
```

---

## 6. Flujo Completo: Del Feature al Localizador

```
┌──────────────────────────────────────────────────────────────────────────┐
│  kudos_category_filter.feature                                          │
│  "Cuando selecciono la categoría 'Innovation'"                          │
└──────────────────────────┬───────────────────────────────────────────────┘
                           │ Cucumber mapea por @Cuando
                           ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  KudosCategorySteps.java                                                │
│  @Cuando("selecciono la categoría {string}")                            │
│  public void selectCategory(String cat) {                               │
│      kudosListPage.selectCategory(cat);  ──────────┐                    │
│  }                                                  │                    │
└─────────────────────────────────────────────────────┼────────────────────┘
                                                      │ Llama al Page Object
                                                      ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  KudosListPage.java                                                     │
│  private final By categoryDropdown = By.id("category-select"); ◄── LOC │
│                                                                         │
│  public void selectCategory(String cat) {                               │
│      WebElement element = find(categoryDropdown);  ◄── WebElement       │
│      new Select(element).selectByVisibleText(cat); ◄── Interacción     │
│  }                                                                      │
└──────────────────────────┬───────────────────────────────────────────────┘
                           │ Selenium WebDriver
                           ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Navegador (Chrome/Firefox)                                             │
│  DOM: <select id="category-select">                                     │
│         <option value="Innovation">Innovation</option> ← seleccionado  │
│       </select>                                                         │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## 7. Ejecución

### Prerrequisitos

- Java 17+
- Gradle 8+ (opcional, para ejecutar con Gradle)
- Chrome o Firefox instalado
- La aplicación corriendo en `http://0.0.0.0:5173`

### Comandos

```bash
# Gradle: ejecutar todos los tests
gradle clean test

# Gradle: ejecutar en modo headless
gradle clean test -Dheadless=true

# Gradle: ejecutar por tags
gradle clean test -Dcucumber.filter.tags="@categoria"
gradle clean test -Dcucumber.filter.tags="@busqueda"
gradle clean test -Dcucumber.filter.tags="@fecha"

# Gradle: tareas auxiliares por tipo
gradle testCategoria
gradle testBusqueda
gradle testFecha

# Gradle: reporte HTML personalizado
gradle clean test -Dcucumber.plugin="html:target/cucumber-report.html"
```

### Reportes

Después de la ejecución, los reportes se encuentran en:
- `target/cucumber-reports/cucumber.html` — Reporte HTML de Cucumber
- `target/cucumber-reports/cucumber.json` — Reporte JSON de Cucumber
- `build/reports/tests/test/index.html` — Reporte de tests cuando ejecutas con Gradle

---

> 📝 **Nota:** Este proyecto fue generado explorando la aplicación con Selenium MCP y documentando los elementos encontrados en la UI de SofkianOS.