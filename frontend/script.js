const API_BASE = "http://localhost:8080/api";

function qs(id) {
  return document.getElementById(id);
}

function goBack() {
  if (window.history.length > 1) {
    window.history.back();
  } else {
    window.location.href = "index.html";
  }
}

async function fetchJson(url, options = {}) {
  const response = await fetch(url, options);
  const text = await response.text();

  let data = null;
  try {
    data = text ? JSON.parse(text) : null;
  } catch {
    data = { raw: text };
  }

  if (!response.ok) {
    throw new Error(data?.error || data?.message || data?.raw || `Request failed with status ${response.status}`);
  }

  return data;
}

async function loadStats() {
  try {
    const data = await fetchJson(`${API_BASE}/stats`);
    if (qs("reportCount")) qs("reportCount").textContent = data.reports ?? 0;
    if (qs("volunteerCount")) qs("volunteerCount").textContent = data.volunteers ?? 0;
    if (qs("resourceCount")) qs("resourceCount").textContent = data.resources ?? 0;
  } catch (error) {
    console.error("Stats error:", error);
  }
}

function renderList(elementId, items, renderer, emptyMessage) {
  const el = qs(elementId);
  if (!el) return;

  if (!items || items.length === 0) {
    el.innerHTML = `<p class="empty-state">${emptyMessage}</p>`;
    return;
  }

  el.innerHTML = items.map(renderer).join("");
}

function reportCard(item) {
  return `
    <div class="result-card">
      <h4>${item.disaster_type || "Unknown"}</h4>
      <p><strong>Place:</strong> ${item.place || "N/A"}</p>
      <p><strong>Description:</strong> ${item.description || "N/A"}</p>
      <p><strong>Severity:</strong> ${item.severity || "N/A"}</p>
      <p><strong>Latitude:</strong> ${item.latitude ?? "N/A"}</p>
      <p><strong>Longitude:</strong> ${item.longitude ?? "N/A"}</p>
      <p><strong>Created At:</strong> ${item.created_at || "N/A"}</p>
    </div>
  `;
}

function volunteerCard(item) {
  return `
    <div class="result-card">
      <h4>${item.name || "Unknown"}</h4>
      <p><strong>Phone:</strong> ${item.phone || "N/A"}</p>
      <p><strong>Skill:</strong> ${item.skill || "N/A"}</p>
      <p><strong>Place:</strong> ${item.place || "N/A"}</p>
      <p><strong>Latitude:</strong> ${item.latitude ?? "N/A"}</p>
      <p><strong>Longitude:</strong> ${item.longitude ?? "N/A"}</p>
      <p><strong>Created At:</strong> ${item.created_at || "N/A"}</p>
    </div>
  `;
}

function resourceCard(item) {
  return `
    <div class="result-card">
      <h4>${item.resource_name || "Unknown"}</h4>
      <p><strong>Quantity:</strong> ${item.quantity ?? "N/A"}</p>
      <p><strong>Contact Person:</strong> ${item.contact_person || "N/A"}</p>
      <p><strong>Contact Phone:</strong> ${item.contact_phone || "N/A"}</p>
      <p><strong>Place:</strong> ${item.place || "N/A"}</p>
      <p><strong>Latitude:</strong> ${item.latitude ?? "N/A"}</p>
      <p><strong>Longitude:</strong> ${item.longitude ?? "N/A"}</p>
      <p><strong>Created At:</strong> ${item.created_at || "N/A"}</p>
    </div>
  `;
}

async function handleSearch() {
  const place = qs("searchPlace") ? qs("searchPlace").value.trim() : "";
  const disasterType = qs("searchDisasterType") ? qs("searchDisasterType").value.trim() : "";

  const url = new URL(`${API_BASE}/search`);
  if (place) url.searchParams.append("place", place);
  if (disasterType) url.searchParams.append("disaster_type", disasterType);

  try {
    const data = await fetchJson(url.toString());
    renderList("reportsResults", data.reports || [], reportCard, "No matching reports found.");
    renderList("volunteersResults", data.volunteers || [], volunteerCard, "No matching volunteers found.");
    renderList("resourcesResults", data.resources || [], resourceCard, "No matching resources found.");
  } catch (error) {
    console.error("Search error:", error);
    alert(`Search failed: ${error.message}`);
  }
}

function clearSearch() {
  if (qs("searchPlace")) qs("searchPlace").value = "";
  if (qs("searchDisasterType")) qs("searchDisasterType").value = "";
  renderList("reportsResults", [], reportCard, "No reports found yet.");
  renderList("volunteersResults", [], volunteerCard, "No volunteers found yet.");
  renderList("resourcesResults", [], resourceCard, "No resources found yet.");
}

function addBackButton() {
  const main = document.querySelector("main");
  if (!main) return;
  if (document.querySelector(".global-back-btn")) return;

  const wrap = document.createElement("div");
  wrap.className = "global-back-btn";
  wrap.style.marginBottom = "18px";
  wrap.innerHTML = `<button class="btn secondary" type="button" onclick="goBack()">← Back</button>`;
  main.prepend(wrap);
}

function wireForms() {
  const reportForm = qs("reportForm");
  const volunteerForm = qs("volunteerForm");
  const resourceForm = qs("resourceForm");

  if (reportForm) {
    reportForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const payload = {
        disaster_type: qs("disasterType")?.value.trim() || "",
        place: qs("reportPlace")?.value.trim() || "",
        description: qs("description")?.value.trim() || "",
        severity: qs("severity")?.value || "",
        latitude: 12.9716,
        longitude: 77.5946
      };

      try {
        const data = await fetchJson(`${API_BASE}/report`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });
        alert(data.message || "Disaster reported successfully");
        reportForm.reset();
        loadStats();
      } catch (error) {
        alert(`Report submit failed: ${error.message}`);
      }
    });
  }

  if (volunteerForm) {
    volunteerForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const payload = {
        name: qs("volunteerName")?.value.trim() || "",
        phone: qs("volunteerPhone")?.value.trim() || "",
        skill: qs("volunteerSkill")?.value.trim() || "",
        place: qs("volunteerPlace")?.value.trim() || "",
        latitude: 12.9716,
        longitude: 77.5946
      };

      try {
        const data = await fetchJson(`${API_BASE}/volunteer`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });
        alert(data.message || "Volunteer registered successfully");
        volunteerForm.reset();
        loadStats();
      } catch (error) {
        alert(`Volunteer submit failed: ${error.message}`);
      }
    });
  }

  if (resourceForm) {
    resourceForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const payload = {
        resource_name: qs("resourceName")?.value.trim() || "",
        quantity: Number(qs("quantity")?.value || 0),
        contact_person: qs("contactPerson")?.value.trim() || "",
        contact_phone: qs("contactPhone")?.value.trim() || "",
        place: qs("resourcePlace")?.value.trim() || "",
        latitude: 12.9716,
        longitude: 77.5946
      };

      try {
        const data = await fetchJson(`${API_BASE}/resource`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });
        alert(data.message || "Resource added successfully");
        resourceForm.reset();
        loadStats();
      } catch (error) {
        alert(`Resource submit failed: ${error.message}`);
      }
    });
  }
}

document.addEventListener("DOMContentLoaded", () => {
  addBackButton();
  loadStats();
  wireForms();

  if (qs("searchBtn")) {
    qs("searchBtn").addEventListener("click", handleSearch);
  }

  if (qs("clearSearchBtn")) {
    qs("clearSearchBtn").addEventListener("click", clearSearch);
  }
});
