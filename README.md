# Jgrep

**Jgrep** is a lightweight, high-performance command-line tool for searching text patterns within files. It supports multiple search algorithms, including **Boyer-Moore**, **Knuth-Morris-Pratt (KMP)**, and **Regex-based searching**, automatically selecting the best strategy for optimal performance.

## Features

- 🔍 **Efficient Searching**: Uses **Boyer-Moore**, **KMP**, or **Regex** dynamically based on the search pattern.
- 📂 **File Handling**: Reads and processes large text files efficiently using **Java Streams**.
- 🏎️ **High Performance**: Implements preallocated data structures for improved search efficiency.
- 🎯 **Pattern Selection Logic**: Automatically chooses the most efficient algorithm based on:
    - Regex patterns → **Regex Search**
    - Patterns with high repetition → **KMP**
    - Short patterns or generic text → **Boyer-Moore**
- 🖥️ **Command-Line Interface**: Simple user input for file path and search pattern.


## How It Works

1. Reads the file content using **BufferedReader**.
2. Selects the appropriate **search algorithm**:
    - Uses **Regex** if the pattern contains special regex characters.
    - Uses **KMP** if the pattern length is large or contains significant repetition.
    - Defaults to **Boyer-Moore** otherwise.
3. Processes each line and records matching results.
4. Displays execution time and the number of matches.

## Search Algorithms

- **Boyer-Moore**: Best for searching long texts with short patterns.
- **KMP (Knuth-Morris-Pratt)**: Efficient for long patterns with repetitive characters.
- **Regex**: Supports complex searches using regular expressions.


All hail to free-software! 🌍