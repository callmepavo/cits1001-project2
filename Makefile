# Builds Aquarium

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Aquarium.java AquariumViewer.java CheckSolution.java Run.java Space.java SimpleCanvas.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
