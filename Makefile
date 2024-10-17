ARCHIVE := 321CD_DincaAlexandraCristina_Tema1.zip

# Parametri pentru compilare.
JFLAGS := -Xlint:unchecked

# Directorul care conține sursele voastre, și cel unde punem binarele.
# Cel mai safe e să le lăsați așa. Dacă schimbați, folosiți path-uri relative!
SRC_DIR := .
OUT_DIR := .

# Compilăm toate sursele găsite în $(SRC_DIR).
# Modificați doar dacă vreți să compilați alte surse.
JAVA_SRC := $(wildcard $(SRC_DIR)/*.java)

JAVA_CLASSES := $(JAVA_SRC:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)
TARGETS := $(JAVA_CLASSES)

.PHONY: build clean pack run-p1 run-p2 run-p3 run-p4 run-p5

build: $(TARGETS)

clean:
	rm -f $(TARGETS) $(OUT_DIR)/*.class $(ARCHIVE)

pack:
	@find $(SRC_DIR) \
		\( -path "./_utils/*" -prune \) -o \
		-regex ".*\.\(cpp\|h\|hpp\|java\)" -exec zip $(ARCHIVE) {} +
	@zip $(ARCHIVE) Makefile
	@[ -f README.md ] && zip $(ARCHIVE) README.md \
		|| echo "You should write README.md!"
	@echo "Created $(ARCHIVE)"

run-p1:
	java -cp $(OUT_DIR) Servere

run-p2:
	java -cp $(OUT_DIR) Colorare

run-p3:
	java -cp $(OUT_DIR) Compresie

run-p4:
	java -cp $(OUT_DIR) Criptat

run-p5:
	java -cp $(OUT_DIR) Oferta

# Schimbați numele surselor și ale binarelor (peste tot).
P1.class: Servere.java
	javac $^

P2.class: Colorare.java
	javac $^

P3.class: Compresie.java
	javac $^

P4.class: Criptat.java
	javac $^

P5.class: Oferta.java
	javac $^

# Reguli pentru compilare. Probabil nu e nevoie să le modificați.

$(JAVA_CLASSES): $(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	javac $< -d $(OUT_DIR) -cp $(SRC_DIR) $(JFLAGS)