# coding=utf-8
from abc import ABCMeta, abstractmethod


# noinspection PyCompatibility
class FormVisitor(metaclass=ABCMeta):
    @abstractmethod
    def visit(self, pql_ast):
        pass

    @abstractmethod
    def form(self, node):
        pass

    @abstractmethod
    def field(self, node):
        pass

    @abstractmethod
    def conditional_if(self, node):
        pass

    @abstractmethod
    def conditional_if_else(self, node):
        pass

    @abstractmethod
    def identifier(self, node):
        pass

    @abstractmethod
    def value(self, node):
        pass
