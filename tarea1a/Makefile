# copiado desde http://mrbook.org/tutorials/make/
CC=gcc
CFLAGS=-Wall -g
LDFLAGS=-Wall -g
SOURCES=tarea1.c ciber.c pc.c
OBJECTS=$(SOURCES:.c=.o)
EXECUTABLE=tarea1

all: $(SOURCES) $(EXECUTABLE)
	
$(EXECUTABLE): $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

%.c%.o:
	$(CC) $(CFLAGS) $< -out $@

clean:
	rm *.o tarea1
