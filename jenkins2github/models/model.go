package models

type Pipeline struct {
	Agent  string
	Stages []Stage
	Post   Post
}

type Stage struct {
	Name  string
	Steps []Step
}

type Step struct {
	Type   string
	Value  string
	Dir    string
	Script string
}

type Post struct {
	Always []string
}
