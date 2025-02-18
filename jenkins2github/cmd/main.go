package main

import (
	"bufio"
	"encoding/json"
	"flag"
	"fmt"
	"os"
	"strings"
	"text/scanner"

	"github.com/slepimis120/seobe/jenkins2github/models"
)

func main() {
	var s scanner.Scanner
	jenkinsFileFlag := flag.String("jenkinsfile", "./Jenkinsfile", "the Jenkinsfile you want to convert")
	flag.Parse()

	content, err := os.ReadFile(*jenkinsFileFlag)
	if err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	modifiedContent := strings.ReplaceAll(string(content), "'", "\"")

	reader := bufio.NewReader(strings.NewReader(modifiedContent))
	s.Init(reader)

	parsedPipeline := parseJenkinsfile(&s)

	jsonData, _ := json.MarshalIndent(parsedPipeline, "", "")
	fmt.Println(string(jsonData))
}

func parseJenkinsfile(s *scanner.Scanner) models.Pipeline {
	var pipeline models.Pipeline

	for {
		tok := s.Scan()
		if tok == scanner.EOF {
			break
		}

		switch s.TokenText() {
		case "pipeline":
			pipeline = parsePipeline(s)
		}
	}
	return pipeline
}

func parsePipeline(s *scanner.Scanner) models.Pipeline {
	var pipeline models.Pipeline
	tok := s.Scan() // Consume '{'

	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}

		switch s.TokenText() {
		case "agent":
			tok = s.Scan() // Consume Agent value
			pipeline.Agent = s.TokenText()
		case "stages":
			pipeline.Stages = parseStages(s)
		case "post":
			pipeline.Post = parsePost(s)
		default:
			fmt.Println("Unknown token:", s.TokenText())
		}
	}
	return pipeline
}

func parseStages(s *scanner.Scanner) []models.Stage {
	var stages []models.Stage

	tok := s.Scan() // Consume '{'
	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}

		if s.TokenText() == "stage" {
			stages = append(stages, parseStage(s))
		}
	}
	return stages
}

func parseStage(s *scanner.Scanner) models.Stage {
	var stage models.Stage
	tok := s.Scan() // Consume '('
	tok = s.Scan()  // Stage name
	stage.Name = s.TokenText()
	stage.Steps = []models.Step{}
	tok = s.Scan() // Consume ')'
	tok = s.Scan() // Consume '{'

	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}
		if s.TokenText() == "steps" {
			stage.Steps = parseSteps(s)
		}
	}

	return stage
}

func parseSteps(s *scanner.Scanner) []models.Step {
	var steps []models.Step

	tok := s.Scan() // Consume '{'
	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}

		switch s.TokenText() {
		case "checkout":
			stepType := s.TokenText()
			tok = s.Scan()
			steps = append(steps, models.Step{Type: stepType, Value: s.TokenText()})
		case "script":
			tok = s.Scan() // Consume '{'
			tok = s.Scan() // Consume script type
			stepType := s.TokenText()
			tok = s.Scan()
			steps = append(steps, models.Step{Type: stepType, Script: s.TokenText()})
			tok = s.Scan() // Consume '}'
		case "dir":
			tok = s.Scan() // Consume '('
			tok = s.Scan() // Consume dir
			dir := s.TokenText()
			tok = s.Scan() // Consume ')'
			tok = s.Scan() // Consume '{'
			tok = s.Scan() // Consume script type
			stepType := s.TokenText()
			tok = s.Scan()
			steps = append(steps, models.Step{Type: stepType, Dir: dir, Script: s.TokenText()})
			tok = s.Scan() // Consume '}'
		default:
			fmt.Println("Unknown step:", s.TokenText())
		}
	}
	return steps
}

func parsePost(s *scanner.Scanner) models.Post {
	var post models.Post
	tok := s.Scan() // Consume '{'
	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}

		if s.TokenText() == "always" {
			post.Always = parseAlways(s)
		}
	}
	return post
}

func parseAlways(s *scanner.Scanner) []string {
	var always []string
	tok := s.Scan() // Consume '{'
	for {
		tok = s.Scan()
		if tok == scanner.EOF || s.TokenText() == "}" {
			break
		}

		always = append(always, s.TokenText())
	}
	return always
}
