import { describe, it, expect, vi } from "vitest";
import { mount } from "@vue/test-utils";
import WelcomeView from "../src/views/WelcomeView.vue";

// 👇 Mock de vue-router para que useRouter no reviente
vi.mock("vue-router", () => ({
  useRouter: () => ({
    push: vi.fn(), // simulamos el método push
  }),
}));

describe("WelcomeView.vue", () => {
  it("should render the welcome view properly", () => {
    const wrapper = mount(WelcomeView);

    // Verificar que se renderiza el título principal
    expect(wrapper.text()).toContain("El Parche Lector");

    // Verificar que existan los botones
    const buttons = wrapper.findAll("button");
    expect(buttons.length).toBe(2);
    expect(buttons[0].text()).toContain("Iniciar sesión");
    expect(buttons[1].text()).toContain("Crear cuenta");
  });
});
